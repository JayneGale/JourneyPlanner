import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;

public class TrieNode {
// from lecture notes
        List<Object> objects; // in this case the objects are Strings (a word) ie a list of chars
        HashMap<Character, TrieNode> children;
// char word;
//        boolean isEnd;
//        int count;

    public void add(char[] word, Object obj) {
//            Set	node to	the	root	of	the	trie;
        TrieNode node = null;
//        for (c : word) {
//                if (!node.children.containsValue(c) {
//                    node.children = c;


//                    create a new child of node, connecting to node via c
//                    move node to the child corresponding to c;
                }
//            node.objects.add(obj)
//            add	obj into	node.objects;

//    public List<Object> get(char[] word) {
//        Set	node to	the	root	of	the	trie;
//        for (c : word) {
//            if (node’s	children	do	not	contain	c)
//            return null;
//            move	node to	the	child	corresponding	to	c;
//        }
//        return node.objects;

//public List<Object> getAll(char[] prefix) {
//    List<Object> results = new ArrayList<Object>();
//    Set	node to	the	root	of	the	trie;
//    for (c : prefix) {
//        if (node’s	children	do	not	contain	c)
//        return null;
//        move	node to	the	child	corresponding	to	c;
//    }
//    getAllFrom(node, results);
//    return results;

//public void getAllFrom(TrieNode node, List<Object> results) {
//    add	node.objects into	results;
//    for (each	child of	node)
//    getAllFrom(child, results);

//        /* Constructor */
//        public TrieNode(char c)
//        {
//            childList = new LinkedList<TrieNode>();
//            isEnd = false;
//            data = c;
//            count = 0;
//        }
//        public TrieNode getChild(char c)
//        {
//            if (childList != null)
//                for (TrieNode eachChild : childList)
//                    if (eachChild.data == c)
//                        return eachChild;
//            return null;
//        }
//    }
}
