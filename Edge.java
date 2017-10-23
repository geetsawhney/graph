package graph;

// this is the edge class
public class Edge {
	Vertex first;
	Vertex second;
	int distance;
	
	public Edge(Vertex first, Vertex second,int distance) {
		this.first = first;
		this.second = second;
		this.distance=distance;
		
	}
}
