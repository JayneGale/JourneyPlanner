import java.util.*;
import java.lang.*;
public class TrieNodeActions {
    private TrieNode root;

    //first step; initialise root TrieNodes to null
    public  TrieNodeActions() {
        root = new TrieNode();
        }

//        public void InitialiseTrie (){
//        root = new TrieNode();
//            HashMap<Character, TrieNode> child = root.GetChild();
//            for (char i = 'a'; i <= 'z'; i++){
//                child.put(i, null); // this sets all children of lower case alphabetic HashMap Key to null
//            }
//            child.put(' ', null); // this adds the special character space
//            child.put('&', null); // this adds the special character &
//            child.put('\'', null); // this adds the special character ' (escaped
//            root.isLast = false;
//        }

    // Extensively used this really helpful Trie tutorial by Amogh Avadhani:
    // https://medium.com/@amogh.avadhani/how-to-build-a-trie-tree-in-java-9d144aaa0d01
// Pseudocode from lecture 2
//    public void add(char[] word, Object obj) {
//        Set	node to	the	root	of	the	trie;
//        for (c : word) {
//            if (node’s	children	do	not	contain	c)
//            create	a	new	child	of	node,	connecting	to	node via	c
//            move	node to	the	child	corresponding	to	c;
//        }
//        add	obj into	node.objects;
//    }
        public void addName(String name) {
        // hmm can I addStop and access the Stop object.name
            HashMap<Character, TrieNode> child = root.GetChild();
            //next step, return if the word is null or we are at the end of the word
            // skip if trying to add a null word
            if (name.length() != 0) {
                for (int i = 0; i < name.length(); i++ ) {
                    //store the each char element c to be the new child until reach the end of the word
                    char c = name.charAt(i);
                    TrieNode tnode;
                    if (child.containsKey(c)) {
                        tnode = child.get(c);
                    }
                    else {
                        tnode = new TrieNode(c);
                        child.put(c, tnode);
                    }
                    child = tnode.GetChild();
                    // once reach the last char iin the word, flag it as the word (set last to true)
                    if (i == name.length() - 1) {
                        tnode.setLast(true);
                        // store the name object // can I store the Stop or Trip object? and access stop_name String name as the object in the Trie at this node
                    }
                }
            }
        }

    public boolean search(String name) {
        HashMap<Character, TrieNode> child = root.GetChild();
        TrieNode tnode = null;
        for(int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if(child.containsKey(c)) {
                tnode = child.get(c);
                child = tnode.GetChild();
            }
            else {
                tnode = null;
                break;
            }
        }
        if(tnode != null && tnode.isLast()) {
            return true;
        }
        else {
            return false;
        }
    }

//    Next make the get and getAll functions
//    Pseudocode from lecture for get a word from a Trie
//    public List<Object> get(char[] word) {
//        Set	node to	the	root	of	the	trie;
//        for (c : word) {
//            if (node’s	children	do	not	contain	c)
//            return null;
//            move	node to	the	child	corresponding	to	c;
//        }
//        return node.objects;
//    }
//    Get All PseudoCode from lecture
//public List<Object> getAll(char[] prefix) {
//    List<Object> results = new ArrayList<Object>();
//    Set	node to	the	root	of	the	trie;
//    getAllFrom(node, results);
//    return results;
//}
//    public void getAllFrom(TrieNode node, List<Object> results) {

    public List<String> getAll(String prefix) {
        HashMap<Character, TrieNode> child = root.GetChild();
        TrieNode tnode = null;
        List<String> results = null;
        // for c in prefix

        for(int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
//    for (c : prefix) {
//        move	node to	the	child	corresponding	to	c;
//    }
            if(child.containsKey(c)) {
                tnode = child.get(c);
                child = tnode.GetChild();
            }
//        else if (node’s	children	do	not	contain	c)
//        return null;
            else {
                tnode = null;
                break;
            }
        }
//        add	node.objects into	results;
//        for (each	child of	node)
//        getAllFrom(child, results);
//    }
//    public List<Object> getAll(String prefix) {

        if(tnode != null && tnode.isLast()) {
//            up to here
//            results.add(prefix);
            return results;
        }
        else {
            return null;
        }
    }

}
