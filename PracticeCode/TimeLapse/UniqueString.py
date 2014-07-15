
#complexity: o(log(n) + k)
# k: position of match

def uniqueString(self):
	print "INPUT DATA: ", self
	sortString = "".join(sorted(self))
	print "Sorted String:", sortString
	l = len(sortString)
	n = 0 
	while (n<l):
		if(n+1 >= l):
			print "Unique String"
			break
		else:
			if(sortString[n]==sortString[n+1]):
				print "Not Unique String"
				print "Breaking point :", sortString[n]
				break
			else:
				n+=2

if __name__=="__main__":
	data = raw_input("Please enter string: ")
	uniqueString(data)