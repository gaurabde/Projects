
def anagramChecker():
	data1 =  list(raw_input("1st String: "))
	data2 =  list(raw_input("2nd String: "))

	if len(data1) == len(data2):
		data1 = sorted(data1)
		print "Sorted-1: ", data1
		data2 = sorted(data2)
		print "Sorted-2: ", data2
		print "==", (data1 ==  data2)
		if data1 == data2:
			print "Strings are anagram"
		else:
			print "Not anagram"
	else:
		print "Not anagram"

if __name__ == "__main__":
	anagramChecker()