import math


def ninetyDegreeRotaion():
	dim = raw_input("Enter Dimension: ")
	matrix = genMatrix(dim)

	midPoint = math.floor(dim/2) - 1
	if dim%2 == 0:
		midPoint -= 1
	else:
		midPoint 
	while(midPoint>0):


def genMatrix(dim):
	matrix = [[0 for x in range(dim)] for x in range(dim)]
	for x in range(dim):
		for y in range(dim):
			matrix[x][y] = x*10 + y

	return matrix