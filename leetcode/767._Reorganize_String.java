

public class Solution {
  
  // Fastest solution - In which we first fill even position and then odd positions
  
  public String reorganizeString(String S) {
        
      int []map = new int[26];
        int maxFreq = -1;
        int maxIndex = -1;
        int index;
        for(char ch : S.toCharArray()) {
            index = ch - 'a';
            map[index]++;
            if(maxFreq < map[index]) {
                maxFreq = map[index];
                maxIndex = index;
            }
        }

      if(maxFreq > S.length() - maxFreq + 1) { return ""; }
      char []result = new char[S.length()];
        
      for(int id = 0; id < result.length; id += 2) {
          while(map[maxIndex] == 0) maxIndex = (maxIndex + 1) % map.length;
          result[id] = (char)(maxIndex + 'a');
          map[maxIndex]--;
      }
     
      for(int id = 1; id < result.length; id += 2) {
          while(map[maxIndex] == 0) maxIndex = (maxIndex + 1) % map.length;
          result[id] = (char)(maxIndex + 'a');
          map[maxIndex]--;     
      }
        return new String(result);
    }

  static class  CFreq {
        int freq;
        char data;
        CFreq(char data, int freq) {
            this.data = data;
            this.freq = freq;
        }
    }
    
  // Faster solution when the charset is fixed here - considering only lower case letter in the alphabet
  
    public String reorganizeString(String S) {
        
      int []map = new int[26];
        for(char ch : S.toCharArray()) {
            map[ch - 'a']++;
        }
      PriorityQueue<CFreq> pq = new PriorityQueue<CFreq>(map.length, (e1, e2) -> {
         if(e1.freq == e2.freq) {
             return e1.data - e2.data;
         }
          return e2.freq - e1.freq;
      });
        
        for(int index = 0; index < map.length; index++) {
            if(map[index] > 0){
            pq.offer(new CFreq((char)(index + 'a'), map[index]));
            }
        }
        
        StringBuilder sb = new StringBuilder();
        CFreq prev = null;
        CFreq current;
        while(!pq.isEmpty()) {
            
            current = pq.poll();
            if(prev != null && prev.freq > 0) { pq.offer(prev); }
            sb.append(current.data);
            current.freq--;
            prev = current;
        }
        return prev.freq > 0 ? "" : sb.toString();
    }
  
  
  // Generic solution using the HashMap - Slower when compared to other solutions
    public String reorganizeString(String S) {
        
      Map<Character, Integer> map = new HashMap<>();
        for(char ch : S.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0)  + 1);
        }
      PriorityQueue<CFreq> pq = new PriorityQueue<CFreq>(map.size(), (e1, e2) -> {
         if(e1.freq == e2.freq) {
             return e1.data - e2.data;
         }
          return e2.freq - e1.freq;
      });
        
        for(char keys : map.keySet()) {
            pq.offer(new CFreq(keys, map.get(keys)));
        }
        
        StringBuilder sb = new StringBuilder();
        CFreq prev = null;
        CFreq current;
        while(!pq.isEmpty()) {
            
            current = pq.poll();
            if(prev != null && prev.freq > 0) { pq.offer(prev); }
            sb.append(current.data);
            current.freq--;
            prev = current;
        }
        return prev.freq > 0 ? "" : sb.toString();
    }
}
