import pyodbc

conn = pyodbc.connect('Driver={SQL Server};Server=tcp:recipebox01.database.windows.net,1433;Database=RecipeDB;Uid=recipeOSU@recipebox01;Pwd=recipe32!;Encrypt=yes;TrustServerCertificate=no;Connection Timeout=30;')
cursor = conn.cursor()
#PUT QUERIES HERE
cursor.execute("Select * from Test")
for row in cursor.fetchall():
	print(row)