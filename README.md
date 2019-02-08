# SimpleFileCompressor
=======================================  
Implement File Compressor with Kotlin  

Works perfectly with .txt files  

before size (original) 1295B  

<img src="./Screenshots/before.png" width="30%" height="30%">  
after size (compressed) 752B  

<img src="./Screenshots/after.png" width="30%" height="30%">  
decompressed 1295B  

<img src="./Screenshots/decomp.png" width="30%" height="30%">  

#TODO
=======================================  
Make it work for all types of files  

### -Guess why it doesn't work with other types-  
I think there is a problem with toString when read file. Maybe string doesn't surpport certain characters.
So, I'll change structure from string-based to byte-based
