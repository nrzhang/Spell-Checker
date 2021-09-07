# Spell-Checker

main method
- When the user selects p, the method will scan through the document one line at a time. This will allow the method to have a line counter.
- The method then splits the line and strips the punctuation. It then checks every word to see if it's in the dictionary
- If it is not in the dictionary, then it will be outputted and the user can decide to ignore, replace or go to the next misspelled word

writeFiles method
- The writeFiles method outputs the corrected and order files.
- When the user selects p, the method will scan through the document one line at a time. This will allow the method to have a line counter.
- The method then splits the line and strips the punctuation. It then checks every word to see if it's in the dictionary
- If it is not in the dictionary, then it will be outputted into the order file with the line number. 
- It will then check the word object to see if it's been ignored or replaced and if so, it will either output the original word, if ignored, or the replacement to the corrected file.
- It will also add the corrected punctuation to the corrected file.  


writeSortedFiles method
- I converted the array of arraylists to an array of arrays

I used the method to replace characters and the method to insert characters for the replacements. 
The delete method is extra credit and it is in both files. 
