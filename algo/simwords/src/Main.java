import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        String[] words1 = new String[]{"great", "acting", "skills"};
        String[] words2 = new String[]{"fine", "drama", "talent"};
        String[][] pairs = new String[][]{
                {"great", "good"},
                {"fine", "good"}, {"drama", "acting"}, {"skills", "talent"}
        };
        Boolean result = (new Main()).areSentencesSimilarTwo(words1, words2, pairs);
        System.out.println("areSentencesSimilarTwo" +
                (result ? "true" : "false"));
    }

    public boolean areSentencesSimilarTwo(String[] words1, String[] words2, String[][] pairs) {
        //todo some string might not be relationship
        Map<String, Set<String>>  map = findSet(pairs);
        addWordsToMap(words1, map);
        addWordsToMap(words2, map);
        Map<Set<String>, Integer> counts1 =getCounts(words1, map);
        Map<Set<String>, Integer> counts2 =getCounts(words2, map);
        if (counts1.size() != counts2.size()) return false;
        for(Set<String> key : counts1.keySet())
        {
            if (!counts2.containsKey(key) || counts1.get(key) != counts2.get(key))
                return false;
        }

        return true;

    }

    Map<Set<String>, Integer> getCounts(String[] words, Map<String, Set<String>>  map){
        Map<Set<String>, Integer> counts = new HashMap<>();
        for(String word : words)
        {
            Set<String> set = map.get(word);
            if (!counts.containsKey(set))
                counts.put(set, 0);
            counts.put(set, counts.get(set) + 1);
        }
        return counts;
    }


    void addWordsToMap(String[] words, Map<String, Set<String>>  map){
        for(String word : words) if (!map.containsKey(word)) {
            Set<String> set = new HashSet<>();
            set.add(word);
            map.put(word, set);
        }
    }


    // Building a similarity tree where each similarity is a set.
    // Store a string to Set map
    // List of sets, merged to new set

    Map<String, Set<String>> findSet(String[][] pairs)
    {
        Map<String, Set<String>> map = new HashMap<>();
        for(String[] pair : pairs) {
            boolean b0 = map.containsKey(pair[0]);
            boolean b1 = map.containsKey(pair[1]);

            if (b0 && b1) {
                if (map.get(pair[0]) != map.get(pair[1])) {
                    HashSet<String> newSet = new HashSet<>(map.get(pair[0]));
                    newSet.addAll(map.get(pair[1]));
                    for (String s : map.get(pair[0]))
                        map.put(s, newSet);
                    for (String s : map.get(pair[1]))
                        map.put(s, newSet);
                }
            } else if (b0 && !b1) {
                Set<String> set = map.get(pair[0]);
                set.add(pair[1]);
                map.put(pair[1], set);
            } else if (b1 && !b0) {
                Set<String> set = map.get(pair[1]);
                set.add(pair[0]);
                map.put(pair[0], set);
            } else {
                HashSet<String> newSet = new HashSet<>();
                newSet.add(pair[0]);newSet.add(pair[1]);
                map.put(pair[0], newSet);
                map.put(pair[1], newSet);
            }
        }

        return map;
    }


}