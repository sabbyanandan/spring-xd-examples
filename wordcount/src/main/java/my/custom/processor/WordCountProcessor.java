package my.custom.processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple word count processor to count the occurrences of 'words' passed
 * through Spring XD source modules such as <code>File</code> or <code>HTTP</code>
 * <p/>
 * Created by Sabby Anandan on 12/26/14.
 */
public class WordCountProcessor {

    /**
     * Simple transform logic that splits by " " pattern and iterates over the collection
     * to aggregate over word repetitions.
     *
     * @param payload - message passed from the inbound channel adapter
     *                (Ex: http or file)
     * @return string - aggregated buckets of words and the count
     */
    public String transform(String payload) {

        Map<String, Integer> splitMap = new HashMap<String, Integer>();

        // Step 1: Return if invalid
        if(payload == null){
            return payload;
        }

        // Step 2: Split the payload by " "
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

        // Step 4: Buckets of words and the respective count
        return splitMap.toString();
    }
}