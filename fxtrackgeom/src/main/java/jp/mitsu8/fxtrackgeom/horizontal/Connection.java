package jp.mitsu8.fxtrackgeom.horizontal;

public class Connection {
	
	private final HorizontalElement elementA;
	private final Edge edgeA;
	private final HorizontalElement elementB;
	private final Edge edgeB;
	
	public Connection(HorizontalElement elementA, Edge edgeA, HorizontalElement elementB, Edge edgeB) {
		this.elementA = elementA;
		this.edgeA = edgeA;
		this.elementB = elementB;
		this.edgeB = edgeB;
	}

	public static enum Edge {
		P0, P1;
	}
	
}
