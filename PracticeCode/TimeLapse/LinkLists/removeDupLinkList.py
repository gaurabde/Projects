
class Node:
	Node next
	data = -99
	def __init__(self):
		self.next = None

	def __init__(self, d):
		self.data = d

	def getData(self):
		return self.data


class LinkListDB:
	Node startNode
	
def main():

