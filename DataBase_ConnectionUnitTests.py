import pyodbc
import pandas as pd
import xlrd 

test = 2 
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

def load_toDb():
	cnxn = pyodbc.connect('DRIVER='+driver+';SERVER='+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
	cursor = cnxn.cursor()
	#For testing purposes, I have only one recipe in this excel sheet, which we will insert into the DB. 
	#Title: Shams' Special 
	book = xlrd.open_workbook("/Users/alikarimyar/Downloads/Recipes.xlsx")
	sheet = book.sheet_by_name("Cookbook")
	query = """INSERT INTO dbo.RecipeBook (ID, Ingredients, Instructions, Picture_link, Title) VALUES (?, ?, ?, ?, ?)"""
	for r in range(1, sheet.nrows):
		if(len(sheet.cell(r,4).value) < 100):
			ID	= sheet.cell(r,0).value
			Ingredients	= sheet.cell(r,1).value
			Instructions = sheet.cell(r,2).value
			Picture_link = sheet.cell(r,3).value
			Title	= sheet.cell(r,4).value
			

			# Assign values from each row
			values = (ID, Ingredients, Instructions, Picture_link, Title)



			# Execute sql Query
			cursor.execute(query, values)

	cursor.commit()
	cursor.close()

##Test to see whether datasets can be loaded into our DB
def Testload_toDb():
	cnxn = pyodbc.connect('DRIVER='+driver+';SERVER='+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
	cursor = cnxn.cursor()
	query = """SELECT Title FROM dbo.RecipeBook WHERE Title LIKE '%Shams%'"""
	cursor.execute(query) 
	data = cursor.fetchone()
	if(data[0] == 'Shams Special'):
		print("Test Passed: Correct Data is inserted in DB")
	else:
		print("Test Failed: Either data incorrectly was inserted into DB or wasn't inserted at all!")

def main():
	if(test == 0):
		db_connection_test()
	elif(test == 1):
		testCount_rows_dataset()
	elif(test == 2):
		Testload_toDb()

if __name__ == "__main__" :
	main()
