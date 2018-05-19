package search.analyzers;

import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing how "relevant" any given document is
 * to a given search query.
 *
 * See the spec for more details.
 */
public class TfIdfAnalyzer {
    // This field must contain the IDF score for every single word in all
    // the documents.
    private IDictionary<String, Double> idfScores;

    // This field must contain the TF-IDF vector for each webpage you were given
    // in the constructor.
    //
    // We will use each webpage's page URI as a unique key.
    private IDictionary<URI, IDictionary<String, Double>> documentTfIdfVectors;

    // Feel free to add extra fields and helper methods.
    
    private IDictionary<URI, Double> normDocumentVectors;
    
    public TfIdfAnalyzer(ISet<Webpage> webpages) {
        this.idfScores = this.computeIdfScores(webpages);
        this.documentTfIdfVectors = this.computeAllDocumentTfIdfVectors(webpages);
        this.normDocumentVectors = normDocuments();
    }

    // Note: this method, strictly speaking, doesn't need to exist. However,
    // we've included it so we can add some unit tests to help verify that your
    // constructor correctly initializes your fields.
    public IDictionary<URI, IDictionary<String, Double>> getDocumentTfIdfVectors() {
        return this.documentTfIdfVectors;
    }


    /**
     * Return a dictionary mapping every single unique word found
     * in every single document to their IDF score.
     */
    private IDictionary<String, Double> computeIdfScores(ISet<Webpage> pages) {

        IDictionary<String, Double> tempDictionary = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> tempDict = new ChainedHashDictionary<String, Double>();
        IDictionary<String, Double> result = new ChainedHashDictionary<String, Double>();
        
        for (Webpage each : pages) {
            IList<String> temp = each.getWords();
            for(String word : temp)
                if(!tempDictionary.containsKey(word)) {
                    tempDictionary.put(word, 1.0);
                    tempDict.put(word, 1.0);
                } else if(!tempDict.containsKey(word)) {
                    tempDictionary.put(word, tempDictionary.get(word) + 1.0);
                    tempDict.put(word, 1.0);
                }
        }
        int div = pages.size();
        
        for(KVPair<String, Double> each : tempDictionary) {
            double input = Math.log(div/each.getValue());
            result.put(each.getKey(), input);
        }
        return result;
//        IDictionary<String, Double> wordsDict = new ChainedHashDictionary<String, Double>();
//        IDictionary<String, Double> newIDFDict = new ChainedHashDictionary<String, Double>();
//        double NumPages = pages.size();
//        for (Webpage page : pages) {
//            for (String word : page.getWords()) {
//                wordsDict.put(word, 0.0);
//            }
//        }
//        
//        for (KVPair<String, Double> wordKV : wordsDict) {
//            String word = wordKV.getKey();
//            int pageCount = 0;
//            for (Webpage innerPage : pages) {
//                if (innerPage.getWords().contains(word)) {
//                    pageCount++;
//                }
//            }
//            
//            // https://docs.oracle.com/javase/8/docs/api/java/lang/Math.html
//            newIDFDict.put(word, Math.log(NumPages/pageCount));
//        }
//
//        return newIDFDict;
        
        
        
    }

    /**
     * Returns a dictionary mapping every unique word found in the given list
     * to their term frequency (TF) score.
     *
     * The input list represents the words contained within a single document.
     */
    private IDictionary<String, Double> computeTfScores(IList<String> words) {
        IDictionary<String, Double> result = new ChainedHashDictionary<String, Double>();
        int totalWords = words.size();
        for (String each : words) {
            if (result.containsKey(each)) {
                result.put(each, result.get(each) + 1.0);
            }else {
                result.put(each, 1.0);
            }
        }
       IDictionary<String, Double> temp = new ChainedHashDictionary<String, Double>();
       for (KVPair<String, Double> each : result) {
           temp.put(each.getKey(), each.getValue()/totalWords);   
       }
       return temp;
    }

    /**
     * See spec for more details on what this method should do.
     */
    private IDictionary<URI, IDictionary<String, Double>> computeAllDocumentTfIdfVectors(ISet<Webpage> pages) {
        // Hint: this method should use the idfScores field and
        // call the computeTfScores(...) method.
        IDictionary<URI, IDictionary<String, Double>> result = new ChainedHashDictionary<URI, IDictionary<String, Double>>();
        
        for (Webpage each : pages) {
            IDictionary<String, Double> tempDictionary;
            IDictionary<String, Double> temp = new ChainedHashDictionary<String, Double>();
            tempDictionary = computeTfScores(each.getWords());
            temp = new ChainedHashDictionary<String, Double>();
            for (String second : each.getWords()) {
                double convert = idfScores.get(second);
                convert *= tempDictionary.get(second);
                temp.put(second, convert);
            }
            result.put(each.getUri(), temp);
        }
        
        return result;
    }

    /**
     * Returns the cosine similarity between the TF-IDF vector for the given query and the
     * URI's document.
     *
     * Precondition: the given uri must have been one of the uris within the list of
     *               webpages given to the constructor.
     */
    public Double computeRelevance(IList<String> query, URI pageUri) {
        // Note: The pseudocode we gave you is not very efficient. When implementing,
        // this method, you should:
        //
        // 1. Figure out what information can be precomputed in your constructor.
        //    Add a third field containing that information.
        //
        // 2. See if you can combine or merge one or more loops.
        
        IDictionary<String, Double> documentVector = documentTfIdfVectors.get(pageUri);//look up document TF-IDF vector using pageUri
        IDictionary<String, Double> queryVector = computeDocumentTfIdfVector(query);//compute query TF-IDF vector
        Double documentNorm = normDocumentVectors.get(pageUri);
        double numerator = 0.0;
        for (String word : query) {
            double docWordScore = 0.0;
            if (documentVector.containsKey(word)) {
                docWordScore = documentVector.get(word);
            }
            double queryWordScore = queryVector.get(word);
            numerator += docWordScore * queryWordScore;
        }
        double denominator = documentNorm * norm(queryVector);

        if (denominator != 0) {
            return numerator / denominator;
        }
        return 0.0;
    }
    
    private IDictionary<String, Double> computeDocumentTfIdfVector(IList<String> query) {
        IDictionary<String, Double> tempDictionary = computeTfScores(query);
        IDictionary<String, Double> temp = new ChainedHashDictionary<String, Double>();
        for (KVPair<String, Double> second: tempDictionary) {
            double convert = 0.0;
            if (idfScores.containsKey(second.getKey())) {
                convert = idfScores.get(second.getKey());
            }
            convert *= second.getValue();
            temp.put(second.getKey(), convert);
        }
        return temp;
    }
    
    private double norm(IDictionary<String, Double> vector) {
        double output = 0.0;
        for (KVPair<String, Double> each : vector) {
            double score = each.getValue();
            output += score * score;
        }
        return Math.sqrt(output);
    }
    
    private IDictionary<URI, Double> normDocuments(){
        IDictionary<URI, Double> result = new ChainedHashDictionary<URI, Double>();
        for (KVPair<URI, IDictionary<String, Double>> each : documentTfIdfVectors) {
            result.put(each.getKey(), norm(each.getValue()));
        }
        return result;
    }
    
}
