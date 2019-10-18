import pyodbc 
import pandas as pd
import xlrd 

server = 'recipebox01.database.windows.net'
database = 'RecipeDB'
username = 'recipeOSU'
password = 'recipe32!'
driver= '{ODBC Driver 17 for SQL Server}'
cnxn = pyodbc.connect('DRIVER='+driver+';SERVER='+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
cursor = cnxn.cursor()

book = xlrd.open_workbook("/Users/alikarimyar/Downloads/Recipes.xlsx")
sheet = book.sheet_by_name("Cookbook")

query = """INSERT INTO dbo.CookBook (Recipes, Ingredients, Steps, Type_food, Cook_time, Servings, Calories, Fat, Carbs, Protein, Cholesterol, Sodium, Sugars) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"""

for r in range(1, sheet.nrows):
		Recipes	= sheet.cell(r,0).value
		Ingredients	= sheet.cell(r,1).value
		Steps = sheet.cell(r,2).value
		Type_food = sheet.cell(r,3).value
		Cook_time	= sheet.cell(r,4).value
		Servings	= sheet.cell(r,5).value
		Calories		= sheet.cell(r,6).value
		Fat		= sheet.cell(r,7).value
		Carbs		= sheet.cell(r,8).value
		Protein		= sheet.cell(r,9).value
		Cholesterol			= sheet.cell(r,10).value
		Sodium			= sheet.cell(r,11).value
		Sugars	= sheet.cell(r,12).value

		# Assign values from each row
		values = (Recipes, Ingredients, Steps, Type_food, Cook_time, Servings, Calories, Fat, Carbs, Protein, Cholesterol, Sodium, Sugars) 



		# Execute sql Query
		cursor.execute(query, values)

cursor.commit()
cursor.close()
