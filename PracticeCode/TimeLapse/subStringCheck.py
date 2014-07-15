
def subStringCheck(string1, string2):
	newTemp = string1 + string1
	if string2 in newTemp:
		print ("Yes \"%s\" is a substring of \"%s\"" %(string2,string1))
	else:
		print ("Yes %s is NOT a substring of %s" %(string2,string1))


if __name__=="__main__":
	#string1 = raw_input("Enter String1: ")
	#string2 = raw_input("Enter String2: ")
	subStringCheck("waterbottle","erbottlewat")
	#subStringCheck(string1,string2)