package ir.ac.aut.ceit.tml.graph;


import java.util.HashMap;

public class Graph {
    private ListNode[] adjList;
    private HashMap<Character, Integer> charToInt;
    private HashMap<Integer, Character> intToChar;
    private int lastCharMap = -1;
    private boolean[] isVisited;

    public Graph(String vertexList){
        // System.out.println(vertexList);
        adjList = new ListNode[vertexList.length()];
        charToInt = new HashMap<>();
        intToChar = new HashMap<>();
        isVisited = new boolean[vertexList.length()];
        int charMap = -1;
        for (char c : vertexList.toCharArray()) {
            charToInt.put(c, ++charMap);
            intToChar.put(charMap, c);
        }

    }

    // a -> b
    public void insertEdge(char a, char b){
        adjList[charToInt.get(a)] = new ListNode(b, adjList[charToInt.get(a)]);
    }


    public void printAdjList(){
        for (int i = 0; i < adjList.length; i++) {
            ListNode listNode = adjList[i];
            System.out.print(intToChar.get(i) + ": ");
            if (listNode != null) {
                listNode.printFromHere();
                System.out.println(" ");
            }
            else {
                System.out.println("[ ]");
            }
        }
        System.out.println(charToInt);
    }

    public void runDFS(char start){
        runDFS(charToInt.get(start));
    }
    private void runDFS(int startVertex) {
        isVisited[startVertex] = true;
        // System.out.println("visited " + intToChar.get(startVertex) + " ");
        ListNode adjVertex = adjList[startVertex];
        while (adjVertex != null) {
            if (isVisited[charToInt.get(adjVertex.getData())] == false) {
                runDFS(charToInt.get(adjVertex.getData()));
            }
            adjVertex = adjVertex.getNext();
        }
    }

    public String getVisitedNodes() {
        String result = "";
        for (int i = 0; i < isVisited.length; i++) {
            if(isVisited[i]){
                result += intToChar.get(i);
            }
        }
        return result;
    }
}
