
import re

def regularExpressionTest():
	hotfix_filter = re.compile("([a-zA-Z]+)([0-9]*)")
	hotfix_split = hotfix_filter.match("LA")

	print "Group: ", len(hotfix_split.groups())
	if len(hotfix_split.groups())>1:
		if hotfix_split.group(2) == "":
			print "None in second"
		print "g1: ",hotfix_split.group(1)
		print "g2: ",hotfix_split.group(2)
	else:
		print "only one: ", hotfix_split.group(1)

if __name__ == "__main__":
	regularExpressionTest()