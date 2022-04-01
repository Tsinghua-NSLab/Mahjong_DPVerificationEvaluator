package libra.bean;

public class Link{
	int inport = -1;
	int outport = -1;

	public Link() {
	}

	public Link(int inport, int outport) {
		this.inport = inport;
		this.outport = outport;
	}
}