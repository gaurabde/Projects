
class Test(object):

	def __init__(self,data=None):
		print "Constructor: ",data

	def test(self,data=None):
		print "test:",data



def main():
	t = Test()
	t2 = Test(10)
	t.test()
	t2.test(20)


if __name__ == "__main__":
	main()