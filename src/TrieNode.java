import java.util.*;
import java.lang.*;
public class TrieNode {
    // this is a TrieNode based on the lowercase alphabet a - z an some special characters
    HashMap<Character, TrieNode> child = new HashMap<>();
//    static final int alphabetsize = 39; // 26 lower case letters, 10 numerals 0-9, and special characters space, ' and &
    boolean isLast = false;
    // this retrieves the object - currently either the stop_name or the trip_name but if I create a generic Type it could potentially retrieve the Stop or Trip object
    String name;
    char c;
    List<String> results; // in this case the objects are Strings (names of stops or trips)

    //constructor for TrieNode from  https://medium.com/@amogh.avadhani/how-to-build-a-trie-tree-in-java-9d144aaa0d01
    public TrieNode() {
    }

    public TrieNode(char c) {
        this.c = c;
    }
    public HashMap<Character, TrieNode> GetChild() {
        return child;
    }
    public void setChild(HashMap<Character, TrieNode> child) {
        this.child = child;
    }

    public boolean isLast() {
        return isLast;
    }
    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }

    public String getName(String name) {return name; } // only used for testing if the name that should be in thr Trie is there
    public void setName(String name) {this.name = name; }

    public List<String> getResults( String prefix) {return results; }
    public void setResults( List<String> results) {this.results = results; }

}





//
//    public void setChildren(HashMap<Character, TrieNode> children) {
//        this.children = children;
//    }
////            node.objects.add(obj)
////            add	obj into	node.objects;
//
////    public List<Object> get(char[] word) {
////        Set	node to	the	root	of	the	trie;
////        for (c : word) {
////            if (node’s	children	do	not	contain	c)
////            return null;
////            move	node to	the	child	corresponding	to	c;
////        }
////        return node.objects;
//
////public List<Object> getAll(char[] prefix) {
////    List<Object> results = new ArrayList<Object>();
////    Set	node to	the	root	of	the	trie;
////    for (c : prefix) {
////        if (node’s	children	do	not	contain	c)
////        return null;
////        move	node to	the	child	corresponding	to	c;
////    }
////    getAllFrom(node, results);
////    return results;
//
////public void getAllFrom(TrieNode node, List<Object> results) {
////    add	node.objects into	results;
////    for (each	child of	node)
////    getAllFrom(child, results);
//
////        /* Constructor */
////        public TrieNode(char c)
////        {
////            childList = new LinkedList<TrieNode>();
////            isEnd = false;
////            data = c;
////            count = 0;
////        }
////        public TrieNode getChild(char c)
////        {
////            if (childList != null)
////                for (TrieNode eachChild : childList)
////                    if (eachChild.data == c)
////                        return eachChild;
////            return null;
////        }
////    }
//}

