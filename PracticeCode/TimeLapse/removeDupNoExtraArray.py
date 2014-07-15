

def removeDupNoExtraArray(dataSet):
	data = list(dataSet)
	startIndex = 0
	lastIndex = len(data) - 1
	lenOfData = len(data)
	while startIndex <= lastIndex:
		tempIndex = startIndex + 1
		while tempIndex < lenOfData:
			if data[tempIndex] == data[startIndex]:
				print "\n======Match found======\nswap:1 ---",data[tempIndex],"-->",data[lastIndex],"\n",data
				swapData = data[tempIndex]
				data[tempIndex] = data[lastIndex]
				data[lastIndex] = swapData
				lastIndex -= 1


				print "\nswap:2 ---",data[startIndex],"-->",data[lastIndex],"\n",data
				swapData = data[startIndex]
				data[startIndex] = data[lastIndex]
				data[lastIndex] = swapData
				lastIndex -= 1

			else:
				tempIndex += 1

		if tempIndex == lenOfData:
			print "No match: ", data[startIndex]
			startIndex += 1

	print "Final output: ", ''.join(data[:lastIndex])



if __name__ == "__main__":
	var = raw_input("Enter the input string: ")
	removeDupNoExtraArray(var)
