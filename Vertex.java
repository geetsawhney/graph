package graph;

import java.util.ArrayList;

public class Vertex {

	private String name;
	private ArrayList<Edge> adjacentEdges;
	
	public Vertex(String name) {
		this.name = name;
		adjacentEdges = new ArrayList<>();
	}

	public int numberEdges() {
		return adjacentEdges.size();
	}

	public boolean isAdjacent(Vertex vertex2) {
		for (Edge e: adjacentEdges) {
			if (e.first == vertex2 || e.second == vertex2) {
				return true;
			}
		}
		return false;
	}

	public void addEdge(Edge e) {
		adjacentEdges.add(e);
	}

	public ArrayList<Vertex> getAdjacentVertices() {
		ArrayList<Vertex> output = new ArrayList<>();
		for (Edge e: adjacentEdges) {
			if (e.first == this) {
				output.add(e.second);
			} else {
				output.add(e.first);
			}
		}
		return output;
	}

	public String getName() {
		return name;
	}

	public void removeEdgeWith(Vertex vertex1) {
		for (Edge e: adjacentEdges) {
			if (e.first == vertex1 || e.second == vertex1) {
				adjacentEdges.remove(e);
				return;
			}
		}
	}

	public void print() {
		StringBuffer s = new StringBuffer();
		s.append(name + ":");
		for (Edge e:adjacentEdges) {
			if (e.first == this)
				s.append(e.second.getName() + ", ");
			else
				s.append(e.first.getName() + ", ");
		}
		System.out.println(s.toString());
	}
	
	
	public int edgeDistance(Vertex ad)
	{
		for(Edge e: adjacentEdges)
		{
			if(e.first==ad || e.second==ad)
				return e.distance;
		}
		return Integer.MAX_VALUE;
	}
	
}