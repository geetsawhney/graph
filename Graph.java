package graph;

import java.util.ArrayList;
import java.util.HashMap;

import stacks_queues.QueueEmptyException;
import stacks_queues.QueueLL;

// This is the Graph class
public class Graph {

	ArrayList<Vertex> vertices;
	String name;


	public Graph(String name) {
		this.name = name;
		vertices = new ArrayList<>();
	}

	public int numberEdges() { // get number of edges in the graph
		int count = 0;
		for (Vertex v: vertices) {
			count += v.numberEdges();
		}
		return count/2;
	}

	public int numberVertices() {
		return vertices.size();
	}

	public boolean isEmpty() {
		return numberVertices() == 0;
	}

	public void addVertex(String newVertex) {
		if (containsVertex(newVertex))
			return;

		Vertex v = new Vertex(newVertex);
		vertices.add(v);
	}

	public void addEdge(String v1, String v2,int distance) {

		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		if (vertex1 == null || vertex2 == null)
			return;

		if (vertex1.isAdjacent(vertex2)) {
			return;
		}

		Edge e = new Edge(vertex1, vertex2, distance);
		vertex1.addEdge(e);
		vertex2.addEdge(e);
	}

	public boolean areAdjacent(String v1, String v2) {
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		if (vertex1 == null || vertex2 == null)
			return false;
		return vertex1.isAdjacent(vertex2);
	}

	public ArrayList<String> getAdjacentVertices(String v1) {
		Vertex vertex1 = getVertex(v1);
		if (vertex1 == null)
			return null;

		ArrayList<Vertex> adjacentVertices = vertex1.getAdjacentVertices();
		ArrayList<String> stringList = new ArrayList<>();
		for (Vertex v: adjacentVertices) {
			stringList.add(v.getName());
		}
		return stringList;
	}

	public void removeVertex(String v1) {
		Vertex vertex1 = getVertex(v1);
		if (vertex1 == null)
			return;

		ArrayList<Vertex> adjacentVertices = vertex1.getAdjacentVertices();
		for (Vertex v: adjacentVertices) {
			v.removeEdgeWith(vertex1);
		}
		vertices.remove(vertex1);
	}

	public void removeEdge(String v1, String v2) {
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		if (vertex1 == null || vertex2 == null)
			return;

		if (!vertex1.isAdjacent(vertex2)) {
			return;
		}

		vertex1.removeEdgeWith(vertex2);
		vertex2.removeEdgeWith(vertex1);
	}

	private boolean hasPath(Vertex v1, Vertex v2, HashMap<Vertex, Boolean> visited) {
		if (v1.isAdjacent(v2))
			return true;

		visited.put(v1, true);
		ArrayList<Vertex> adjacent = v1.getAdjacentVertices();

		for (Vertex v: adjacent) {
			if (visited.containsKey(v))
				continue;

			if (hasPath(v, v2, visited)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPath(String v1, String v2) {
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		if (vertex1 == null || vertex2 == null)
			return false;

		//		HashMap<Vertex, Boolean> visited = new HashMap<>();
		return hasPath(vertex1, vertex2,  new HashMap<Vertex, Boolean>());
	}

	public void printGraph() {
		for (Vertex v: vertices) {
			v.print();
		}
	}

	private Vertex getVertex(String name) {
		for (Vertex v:vertices) {
			if (v.getName().equals(name))
				return v;
		}
		return null;
	}

	public boolean containsVertex(String name) {
		for (Vertex v:vertices) {
			if (v.getName().equals(name))
				return true;
		}
		return false;
	}

	public boolean hasPath_BFS(String v1,String v2) // check path using bfs
	{
		Vertex vertex1 = getVertex(v1);
		Vertex vertex2 = getVertex(v2);
		if (vertex1 == null || vertex2 == null)
			return false;


		QueueLL<Vertex> queue=new QueueLL<Vertex>();
		queue.enqueue(vertex1);

		HashMap<Vertex, Boolean> visited=new HashMap<>();

		while(!queue.isEmpty())
		{
			Vertex next;

			try 
			{
				next=queue.dequeue();
			} catch (QueueEmptyException e)
			{
				return false;
			}

			if(next.isAdjacent(vertex2))
				return true;

			visited.put(next, true);

			ArrayList<Vertex> adjacent=next.getAdjacentVertices();
			for(Vertex v: adjacent)
			{
				if(visited.containsKey(v))
					continue;

				queue.enqueue(v);
			}
		}
		return false;
	}

	public boolean isConnected() //check if graph is connected
	{
		if(vertices.size()<2)
		{
			return true;
		}

		QueueLL<Vertex> queue=new QueueLL<Vertex>();
		queue.enqueue(vertices.get(0));

		HashMap<Vertex, Boolean> visited=new HashMap<Vertex, Boolean>();

		while(!queue.isEmpty())
		{
			Vertex next;

			try {
				next=queue.dequeue();
			} catch (QueueEmptyException e) {
				return false;
			}

			if(visited.containsKey(next))
			{	continue;}

			visited.put(next, true);

			ArrayList<Vertex> adjacent=next.getAdjacentVertices();

			for(Vertex v: adjacent)
			{
				if(visited.containsKey(v))
					continue;

				queue.enqueue(v);
			}
		}

		if(visited.size()==numberVertices())
			return true;
		else 
			return false;
	}

	public ArrayList<ArrayList<String>> connectedComponents()
	{

		if(isEmpty())
			return null;

		ArrayList<ArrayList<String>> output=new ArrayList<>();
		HashMap<Vertex, Boolean> visited=new HashMap<Vertex, Boolean>();

		QueueLL<Vertex>queue=new QueueLL<Vertex>();

		for(Vertex v: vertices)
		{

			ArrayList<String> connections = null;

			if(!visited.containsKey(v))
			{
				queue.enqueue(v);
				connections=new ArrayList<>();
			}

			while(!queue.isEmpty())
			{
				Vertex next;
				try 
				{
					next=queue.dequeue();
				} catch (QueueEmptyException e) {
					return null;
				}

				if(!visited.containsKey(next))
				{
					visited.put(next, true);
					connections.add(next.getName());
				}
				ArrayList<Vertex> adjacent=next.getAdjacentVertices();

				for(Vertex v1: adjacent)
				{
					if(visited.containsKey(v1))
						continue;

					queue.enqueue(v1);
				}
			}

			if(connections!=null)
				output.add(connections);
		}

		return output;
	}

	public boolean checkBipartite()
	{
		HashMap<Vertex, Boolean> first=new HashMap<Vertex, Boolean>();
		HashMap<Vertex, Boolean> second=new HashMap<Vertex, Boolean>();

		boolean isFirstTurn=true;
		QueueLL<Vertex> primary=new QueueLL<Vertex>();
		QueueLL<Vertex> temporary=new QueueLL<Vertex>();


		for(Vertex v: vertices)
		{
			if(!first.containsKey(v) && !second.containsKey(v))
				primary.enqueue(v);

			while(!primary.isEmpty())
			{

				if(isFirstTurn)
				{
					while(!primary.isEmpty())
					{			
						Vertex next;

						try {
							next=primary.dequeue();
						} catch (QueueEmptyException e) {
							next=null;
						}

						for(Vertex adjacent: next.getAdjacentVertices())
						{
							if(first.containsKey(adjacent))
								return false;
							if(!second.containsKey(adjacent))
								temporary.enqueue(adjacent);
						}
						first.put(next, true);
					}
					isFirstTurn=!isFirstTurn;
				}

				else
				{
					while(!primary.isEmpty())
					{			
						Vertex next;

						try {
							next=primary.dequeue();
						} catch (QueueEmptyException e) {
							next=null;
						}

						for(Vertex adjacent: next.getAdjacentVertices())
						{
							if(second.containsKey(adjacent))
								return false;
							if(!first.containsKey(adjacent))
								temporary.enqueue(adjacent);
						}
						second.put(next, true);
					}
					isFirstTurn=!isFirstTurn;
				}
				primary=temporary;
				temporary=new QueueLL<Vertex>();
			}
		}

		for(Vertex v: first.keySet())
		{
			System.out.print(v.getName()+" ");
		}
		System.out.println();
		for(Vertex v: second.keySet())
		{
			System.out.print(v.getName()+" ");
		}
		return true;
	}

	public HashMap<String, Integer> findShortestDistance_1(String source) //Dijstras algorithm
	{
		ArrayList<Pair> leftVertices=new ArrayList<Pair>();
		HashMap<String, Integer> output=new HashMap<>();

		for(Vertex v: vertices)
		{
			Pair p=new Pair();
			p.v=v;
			p.distance=Integer.MAX_VALUE;
			if(v.getName().equals(source))
				p.distance=0;
			leftVertices.add(p);
		}

		while(!leftVertices.isEmpty())
		{
			Pair p=null;
			for(Pair p1:leftVertices)
			{
				if(p==null || p.distance>p1.distance)
					p=p1;
			}

			output.put(p.v.getName(), p.distance);
			ArrayList<Vertex> adjacent=p.v.getAdjacentVertices();

			for(Vertex ad: adjacent)
			{
				for(Pair left: leftVertices)
				{
					if(left.v.equals(ad))
					{
						if(left.distance> p.distance+p.v.edgeDistance(ad))
							left.distance=p.distance+p.v.edgeDistance(ad);
					}
				}
			}
			leftVertices.remove(p);	
		}
		return output;
	}

	public HashMap<String, Integer> findShortestDistance_2(String source) //Dijstras algorithm
	{
		HashMap<String, Integer> output=new HashMap<>();
		HashMap<Vertex, Integer> leftVertices=new HashMap<>(); 
		for(Vertex v: vertices)
		{
			if(v.getName().equals(source))
				leftVertices.put(v, 0);
			else
				leftVertices.put(v, Integer.MAX_VALUE);
		}

		while(!leftVertices.isEmpty())
		{
			Vertex min=null;
			for(Vertex left :leftVertices.keySet())
			{
				if(min==null|| leftVertices.get(left)<leftVertices.get(min))
					min=left;
			}

			for(Vertex ad: min.getAdjacentVertices())
			{	
				if(leftVertices.containsKey(ad)){
					if(leftVertices.get(ad)>leftVertices.get(min)+min.edgeDistance(ad))
						leftVertices.put(ad,leftVertices.get(min)+min.edgeDistance(ad));
				}
			}

			output.put(min.getName(), leftVertices.get(min));
			leftVertices.remove(min);
		}
		return output;
	}
}
