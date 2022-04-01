package libra.bean;

import java.util.ArrayList;
import java.util.HashMap;

import bean.basis.BasicTF;
import bean.basis.Rule;
import interfaces.Header;
import interfaces.TransferFunc;

public class LibraTransFunc implements TransferFunc{
	public static ArrayList<Subnet> subnets = new ArrayList<Subnet>();
	BasicTF TF;
	public int nextID = 0;
	public int ruleID = 0;
	
	public static void transferBasicTF(BasicTF TF) {
		new LibraTransFunc(TF);
	}
	
	public LibraTransFunc(BasicTF TF) {
		this.TF = TF;
		generateSubnets();
		setRules();
		System.out.print("Finish Graphic Ruleset Generation");
	}
	public int generateNextID() {
		return nextID++;
	}
	public String generateRuleID() {
		return "" + (ruleID++);
	}
	
	public void generateSubnets() {
		for(String key: TF.getIdToRule().keySet())
		{
			Rule rule = TF.getIdToRule().get(key);
			if(rule.getAction()=="fwd") {
				Subnet temp = new Subnet(rule.getMatch());
				for(int inport : rule.getInPorts()) {
					for(int outport : rule.getOutPorts()) {
						temp.getLinks().add(new Link(inport, outport));
					}
				}
				this.addSubnet(temp);
			}
		}
	}
	
	public void setRules() {
		this.TF.rules.clear();
		this.TF.idToRule.clear();
		this.TF.idToAffectedBy.clear();
		this.TF.idToInfluenceOn.clear();
		this.TF.isUncovered = true;
		for(Subnet subnet : subnets) {
			for(Link link : subnet.links) {
				Rule rule = new Rule();
				rule.getInPorts().add(link.inport);
				rule.getOutPorts().add(link.outport);
				rule.setMatch(subnet.header);
				this.TF.rules.add(rule);
				this.TF.idToRule.put(this.generateRuleID(), rule);
			}
		}
	}
	
	public void addSubnet(Subnet subnet) {
		if(subnet.isEmpty()) {
			return;
		}else if(subnets.isEmpty()) {
			Subnet temp = new Subnet(subnet);
			temp.setId(generateNextID());
			Subnet tempComp = temp.complement();
			tempComp.setId(generateNextID());
			subnets.add(temp);
			subnets.add(tempComp);
		}else {
			Subnet temp = new Subnet(subnet);
			Subnet tempComp = temp.complement();
			ArrayList<Subnet> newSubnets = new ArrayList<Subnet>();
			for(Subnet existSubnet: subnets) {
				//A^!B
				Subnet comp = tempComp.copyAnd(existSubnet);
				if(!comp.isEmpty()) {
					comp.setId(generateNextID());
					newSubnets.add(comp);
				}
				//A^B
				existSubnet.and(temp);
				if(!existSubnet.isEmpty()) {
					newSubnets.add(existSubnet);
				}
			}
			subnets = newSubnets;
		}
	}
	
}