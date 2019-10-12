import pyodbc
import pandas as pd


def connect_to_DB():
	server = 'recipebox01.database.windows.net'
	database = 'RecipeDB'
	username = 'recipeOSU'
	password = 'recipe32!'
	driver= '{ODBC Driver 17 for SQL Server}'
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

def load_dataset(excel_file, cursor):
	query = "SELECT count(*) FROM Cookbook"
	cursor.execute(query)
	data = cursor.fetchall() 
	print(data)
	#data = pd.read_excel(excel_file, sheet_name = 'Cookbooks') 


def main():
	excel_file = '/Users/alikarimyar/Downloads/Recipes.xlsx' 
	db_connection_test(); 
	load_dataset(excel_file, cursor)

if __name__ == "__main__" :
	main()