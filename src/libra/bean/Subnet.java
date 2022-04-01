package libra.bean;

import java.util.ArrayList;
import java.util.HashSet;

import apverifier.bean.Predicate;
import interfaces.Header;

public class Subnet{
	int Id = -1;
	Header header = null;
	HashSet<Link> links = new HashSet<Link>();
	
	public Subnet() {
		
	}
	public Subnet(Header header) {
		this.header = header.copy();
	}
	public Subnet(Subnet subnet) {
		this.Id = subnet.Id;
		this.header = subnet.getHeader().copy();
		this.links.addAll(subnet.getLinks());
	}
	public boolean isEmpty() {
		return this.header.isEmpty();
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public HashSet<Link> getLinks() {
		return links;
	}
	public void setLinks(HashSet<Link> links) {
		this.links = links;
	}
	public Subnet complement() {
		return new Subnet(header.copyComplement());
	}
	public void and(Subnet other) {
		this.header.and(other.getHeader());
		this.links.addAll(other.getLinks());
	}
	public Subnet copyAnd(Subnet other) {
		Subnet result = new Subnet(this);
		result.getHeader().and(other.getHeader());
		result.getLinks().addAll(other.getLinks());
		return result;
	}
}