import pyodbc
import pandas as pd

test = 1 
server = 'recipebox01.database.windows.net'
database = 'RecipeDB'
username = 'recipeOSU'
password = 'recipe32!'
driver= '{ODBC Driver 17 for SQL Server}'

def connect_to_DB():

	cnxn = pyodbc.connect('DRIVER='+driver+';SERVER='+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
	cursor = cnxn.cursor()
	if(not cursor):
		return 0
	return 1 

##Connection Unit Test: Check to see if the database connectivity works 
def db_connection_test(): 
	connection = connect_to_DB() 
	if(connection == 1):
		print("Test Passed: Succesfully Connected to the Database")
	else:
		print("Test Failed: Cannot Connect to the Database")

def count_rows_dataset():
	cnxn = pyodbc.connect('DRIVER='+driver+';SERVER='+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
	cursor = cnxn.cursor()
	query = "SELECT count(*) FROM RecipeBook"
	cursor.execute(query)
	data = cursor.fetchone() 
	return data[0]; 

##Row Count Test: Check to see if the database returns the correct number of rows
def testCount_rows_dataset():
	numRows = count_rows_dataset() 
	##Currently there are 39,522 recipes in the database. 0
	if(numRows == 39522):
		print("Test Passed: Correct Number of Rows were returned!")
	else:
		print("Test Failed: Incorrect Number of Rows were Returned!")

def main():
	if(test == 0):
		db_connection_test()
	elif(test == 1):
		testCount_rows_dataset()

if __name__ == "__main__" :
	main()
