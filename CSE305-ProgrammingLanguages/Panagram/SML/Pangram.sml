fun hw1(inFile:string, outFile:string) = 
	let
		val inStream = TextIO.openIn inFile
		val outStream = TextIO.openOut outFile
		val readLine = TextIO.inputLine inStream
		fun helper(readLine: string option) =
			let 
				fun pangram(i: int, str: string) =
					let
						val a = "abcdefghijklmnopqrstuvwxyz"
					in	
						if Char.contains str(String.sub(a, i)) then 
							( if i+1 = String.size(a) then "true\n"
							  else pangram(i+1, str) )
						else "false\n"
													
					end
											
			in
				case readLine of
                    NONE => ( TextIO.closeIn inStream; TextIO.closeOut outStream)
					| SOME(c) => (TextIO.output(outStream, pangram(0, c));
					helper(TextIO.inputLine inStream))
			end
	in
		helper(readLine)
	end;	
