package my.custom.processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple word count custom processor to count the occurrences of 'words' passed
 * through Spring XD source modules such as <tt>File</tt>, <tt>HTTP</tt>, etc.
 * <p/>
 * Created by Sabby Anandan on 12/26/14.
 */
public class WordCountProcessor {

    /**
     * Simple transform logic that splits the payload and iterates over the collection
     * to aggregate on word repetitions.
     *
     * @param payload - message passed from the inbound channel adapter
     *                (Ex: http or file)
     * @return string - aggregated buckets of words and the respective count passed to the
     *                outboud channel adapter (Ex: log)
     */
    public String transform(String payload) {

        Map<String, Integer> splitMap = new HashMap<String, Integer>();

        // Step 1: Return if invalid
        if (payload == null) {
            return payload;
        }

        // Step 2: Split the payload
        String[] words = payload.split(" ");
        // Step 3: Iterate, identify, bucket and increment count on repetition
        for (String word : words) {
            if (splitMap.containsKey(word)) {
                int counter = splitMap.get(word);
                splitMap.put(word, ++counter);
            } else {
                splitMap.put(word, 1);
            }
        }

        // Step 4: Returns buckets of words and the respective count
        return splitMap.toString();
    }
}