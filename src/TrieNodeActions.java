import java.util.*;
import java.lang.*;
public class TrieNodeActions {
    private TrieNode root;
    private ArrayList<Character> charsInTrie;

    //first step; initialise root TrieNodes to null
    public TrieNodeActions() {
        root = new TrieNode();
    }

    // These are all my TrieNode methods
// // add a stop or trip name to the trie
    // check if a stop or tripname is in the tree
    // get a trip or stop name
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
            for (int i = 0; i < name.length(); i++) {
                // convert the name into lower case characters to reduces  tree width and its more user friendly (case insensitive) to search stop names;
                char c = name.charAt(i);
//                char c = name.charAt(i);
                //store the each char element c to be the new child until reach the end of the word
                TrieNode tnode;
                if (child.containsKey(c)) {
                    tnode = child.get(c);
                } else {
                    tnode = new TrieNode(c);
                    child.put(c, tnode);
                }
                child = tnode.GetChild();
                // once reach the last char iin the word, flag it as the word (set last to true)
                if (i == name.length() - 1) {
                    tnode.setLast(true);
                    tnode.setName(name);
                    // store the name object // can I store the Stop or Trip object? and access stop_name String name as the object in the Trie at this node
                }
            }
        }
    }

    public boolean searchBool(String name) {
        HashMap<Character, TrieNode> child = root.GetChild();
        TrieNode tnode = null;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (child.containsKey(c)) {
                tnode = child.get(c);
                child = tnode.GetChild();
            } else {
                tnode = null;
                break;
            }
        }
        if (tnode != null && tnode.isLast()) {
            return true;
        } else {
            return false;
        }
    }

    //next make the get function
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
// I will call mine searchName(String) instead of get(char)
    public String searchName(String name) {
        HashMap<Character, TrieNode> child = root.GetChild();
        TrieNode tnode = null;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (child.containsKey(c)) {
                tnode = child.get(c);
                child = tnode.GetChild();
            } else {
                tnode = null;
                break;
            }
        }
        if (tnode != null && tnode.isLast()) {
            return name;
        } else {
            return null;
        }
    }

//    Next make the getAll function for a prefix
    // the first bit of the code get a prefix is the same as get a word up to the end of the prefix. If the prefix contains a whole result, add that into the results List,string>

//    GetAll() PseudoCode from lecture
//public List<Object> getAll(char[] prefix) {
//    List<Object> results = new ArrayList<Object>();
//    Set	node to	the	root	of	the	trie;
//    getAllFrom(node, results);
//    return results;
//}

    public List<String> getAllFromPrefix(String prefix) {
        HashMap<Character, TrieNode> child = root.GetChild();
        TrieNode tnode = null;
        List<String> results = new ArrayList<String>();
        // for c in prefix
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
//    for (c : prefix) {
//        move	node to	the	child	corresponding	to	c;
            if (child.containsKey(c)) {
                tnode = child.get(c);
                child = tnode.GetChild();
//                System.out.println("GetAllfromPrefix Ln 134 prefix  " + prefix + " c " + c); // there are 63 chars in the names; could reduce this by toLower
                // we don't add in any results before the end of the prefix because if the prefix eg Gurd ( 568) already contains a complete name eg Gurd, we don't add Gurd to the list if they type more
            }
//        else if (node’s	children	do	not	contain	c)        return null; // because there is no branch that contains the prefix at all
            else {
                tnode = null;
                break;
            }
        }
        //once we reach the end of the prefix word, check if its a complete name just in case they have typed the whole name
        // if the prefix alone is a stop name, add it to the results list but carry on down the trie, it may not be the only last in this branch
        // name should equal prefix here
        if (tnode.isLast()) {
            String name = tnode.getName(tnode.name);
            results.add(name);
            System.out.println("prefix  " + prefix + " name " + name); // there are 41 chars in the Trie
        }
        results = getAll(tnode, results, charsInTrie);
//      from lecture notes:
//      add	node.objects into	results;
//        for (each	child of	node)
//        getAllFrom(child, results);
//    }
//    public List<Object> getAll(String prefix)
        int num = results.size();
        System.out.println("on coming back from getAll to getAllfrom (ln 159) results size = " + num);
        if(num <=0){
            System.out.println("getAllfrom ln 161 something has gone wrong results is empty");
        }
        else {
            if (num > 5) {
                num = 5;
            }
            for (int i = 0; i < num; i++) {
                System.out.println("getAllfrom ln 154 end results size =  " + results.get(i));
            }
        }
        return results;
    }

    public List<String> getAll(TrieNode tnode, List<String> results, List<Character> charsInTrie) {
        // get all the Trienodes below the prefix by searching through all the characters that might be in the branch charsInTrie
        int count = 0;
        HashMap<Character, TrieNode> child = tnode.GetChild();
        if(child == null){
            System.out.println("getAll ln 180 child is null " + " count " + count);
            return results;
        }
        for (TrieNode t : child.values()){
            if (t.isLast) {
                results.add(t.name);
                System.out.println("getAll ln 205 t.name " + t.name + " resutls size " + results.size());
            }
            results = getAll(t, results, charsInTrie);
        }
        return results;
    }
}


