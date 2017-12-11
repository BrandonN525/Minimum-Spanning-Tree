package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		
		PartialTreeList treeList = new PartialTreeList();
		
		for (int i = 0; i < graph.vertices.length; i++){
			PartialTree partial = new PartialTree(graph.vertices[i]);
			Vertex ver = graph.vertices[i];
			Vertex.Neighbor neigh = ver.neighbors;
			while (neigh != null){
				PartialTree.Arc arch = new PartialTree.Arc(ver, neigh.vertex, neigh.weight);
				partial.getArcs().insert(arch);
				neigh = neigh.next;
			}
			treeList.append(partial);
			
			
		}
		
		return treeList;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> archList = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() > 1) {
			PartialTree partial = ptlist.remove();
			if (partial == null){
				break;
			}
			MinHeap<PartialTree.Arc> partHeap = partial.getArcs();
			PartialTree.Arc arch = partHeap.deleteMin();
			Vertex ver = arch.v2;
			Vertex ver2 = ver.getRoot();
			Vertex ver3 = partial.getRoot();
			while(ver2 == ver3) {
				arch = partHeap.deleteMin();
				ver = arch.v2;
			}
			PartialTree partTree = ptlist.removeTreeContaining(ver);
			archList.add(arch);
			partial.merge(partTree);
			ptlist.append(partial);
			
		}

		return archList;

	}
}
