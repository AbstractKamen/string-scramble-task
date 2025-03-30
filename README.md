String Scramble Task
Find all nine-letter words for which the following is true:
- after removing a single letter from the word the remainder characters also form a valid word
- after removing another letter from the now eight-letter word the remainder still form a valid word
- remove characters until the last valid words can exist `A` `I`
  Example:
```
  |    word     |  len  |
  | 'STARTLING' |  9    |
  | 'START_ING' |  8    |
  | 'STAR__ING' |  7    |
  | 'ST_R__ING' |  6    |
  | 'ST____ING' |  5    |
  | 'S_____ING' |  4    |
  | 'S_____IN_' |  3    |
  | '______IN_' |  2    |
  | '______I__' |  1    |
```
  Additional Requirements:
1. Optimise for speed -> should run in less than two seconds
2. Valid words from the english language should be downloaded from here:
   - https://raw.githubusercontent.com/nikiiv/JavaCodingTestOne/master/scrabble-words.txt
3. Actual download of words content should not be part of the 'less than two seconds constraint'

Build:
- Requires java 23
- Maven 3.8+
```
mvn package

```
Run either:
- run the bash script
- run the built jar
```
solutionChoice can be ->  prepset | noprep | preptrie
other args are optional
java -jar target/count-english-words-0.0.1-SNAPSHOT.jar <solutionChoice> [-targetLen <integer>] [-url <dictionaryUrl>] [-debug] 
```

Solution times on my machine:

$ sh count-english-words.sh
```
prepset

Preprocessing Set Solution with target len [9]
Read Input [279496] lines in [00:00:00.294]
Found [775] valid words in [00:00:00.243]

real    0m1.495s
user    0m0.000s
sys     0m0.000s
```
```
preptrie

Preprocessing Trie Solution with target len [9]
Read Input [279496] lines in [00:00:00.295]
Found [775] valid words in [00:00:00.065]

real    0m1.036s
user    0m0.000s
sys     0m0.000s
```
```
noprep

No Preprocessing Set Solution with target len [9]
Read Input [279496] lines in [00:00:00.339]
Found [775] valid words in [00:00:00.244]

real    0m1.258s
user    0m0.000s
sys     0m0.000s
```
