import java.util.*;
import java.lang.*;
public class TrieNode {
// this is a TrieNode based on the lowercase alphabet a - z an some special characters
    HashMap<Character, TrieNode> child;
    static final int alphabetsize = 39; // 26 lower case letters, 10 numerals 0-9, and special characters space, ' and &
    boolean isLast = false;
    List<String> names; // in this case the objects are Strings (names of stops or trips)

//    a helper function to convert the name List to a List of Char Arrays
    public List<char[]>NamestoCharArrays(List<String> names) {
        // Convert the String lists ( of stop and trip names) to a list of lowercase char arrays
        List<char[]> namesAsChar = new ArrayList<>();
        for (String n : names) {
            int nameLength = n.length();
            char[] nameAsChar = n.toCharArray();
            if (nameLength == 0) {
                continue;
            }
//            make all the Characters lower case to simplify the Trie
            for (int i = 0; i < nameLength; i++) {
                nameAsChar[i] = Character.toLowerCase(nameAsChar[i]);
            }
            namesAsChar.add(nameAsChar);
        }
        return namesAsChar;
        //check this has the same index list as names
    }

    public TrieNode addName(char[] nameAsChar, TrieNode root) {
        // call this for each name in names, after converting names using NamestoCharArrays
        // the object stored is name, the String, but the children are the chars
        child = new HashMap<Character, TrieNode>();
        //first step; initialise root TrieNodes to null

//        how to declare a hashmap
//        public static void main(String args[]){
//   HashMap<Integer,String> map=new HashMap<Integer,String>();//Creating HashMap
//   map.put(1,"Mango");  //Put elements in Map
//   map.put(2,"Apple");
//   map.put(3,"Banana");
//   map.put(4,"Grapes");
        for (char i = 'a'; i <= 'z'; i++){
            child.put(i, null); // this sets all children of lower case alphabetic HashMap Key to null
        }
        child.put(' ', null); // this adds the special character space
        child.put('&', null); // this adds the special character &
        child.put('\'', null); // this adds the special character ' (escaped
        isLast = false;
        //next step, return if the word is null or we are at the end of the word
        int len =  nameAsChar.length;
        if (len == 0) {
            return root;
        }

        //now store the first char element c to be the new child and remove it from the nameAsChar array
        char c = nameAsChar[0];
        // found this way to remove the first element from the array source https://stackoverflow.com/questions/3663944/what-is-the-best-way-to-remove-the-first-element-from-an-array
        char[] clippedName = new char[len - 1];
        System.arraycopy(nameAsChar,1,clippedName,0,len -1);
        TrieNode current = root;
        for (char each : nameAsChar) {
            TrieNode node = current.child.get(each);
            if (node == null) {
                return current;
            }
            current = node;
        }
        current.isLast = true;
        return current;
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
    }

