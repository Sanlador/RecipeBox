import requests
from urllib.request import urlopen
from lxml import html
from bs4 import BeautifulSoup
from selenium import webdriver
import re

def getURLs(): 
	url = 'https://www.allrecipes.com/?page=1'
	html = requests.get(url)
	soup = BeautifulSoup(html.content, 'html.parser')
	links = []
	for a in soup.findAll('a', attrs ={'href': re.compile('^https://www.allrecipes.com/recipe/')}):
		links.append(a['href'])

	list_urls = list(set(links))
	return list_urls; 

def scrape():
	images = []
	##links = getURLs() 
	##first = links[0]
	first = 'https://www.allrecipes.com/recipe/34613/roquefort-pear-salad/?internalSource=hub%20recipe&referringContentType=Search&clickId=cardslot%201'
	##data = requests.get(first)
	##soup = BeautifulSoup(data.text, 'html.parser')

	page = requests.get(first)
	tree = html.fromstring(page.content)
	
	pictures = tree.xpath("//img/@src")
	for picture in pictures:
		if picture[-4:] == '.jpg':
			images.append(picture)

	# print(images)
	# print('\n')

	foodCategories = tree.xpath('//meta[@itemprop="recipeCategory"]/@content')
	# print('Recipe Categorie(s):', foodCategories)
	# print('\n')

	servings = tree.xpath('//meta[@itemprop="recipeYield"]/@content')
	# print('Servings:', servings)
	# print('\n')

	titles = tree.xpath('//h1[@itemprop="name"]/text()')
	# print('Title:', titles)
	# print('\n')

	ingredients = tree.xpath('//span[@itemprop="recipeIngredient"]/text()')
	# print('Ingredients:', ingredients)

	instructions = tree.xpath('//span[@class="recipe-directions__list--item"]/text()')
	instructions = [step.replace('\n', '') for step in instructions]
	instructions = [step.replace('                            ', '') for step in instructions]

	cookTime = tree.xpath('//span[@class="prepTime__item--time"]/text()')
	if(len(cookTime) > 3):
		cookTime = cookTime[-2:]
		cookTime = int(cookTime[0])*60 + int(cookTime[1])
	else:
		cookTime = cookTime[2]

	calorieCount = tree.xpath('//span[@itemprop="calories"]/text()')
	calorieCount = [step.replace(' calories;', '') for step in calorieCount]
	calorieCount = calorieCount[0]
	# print('Calories:', calories)
	# print('\n')

	fat = tree.xpath('//span[@itemprop="fatContent"]/text()')
	fat = [fats.replace(' ', '') for fats in fat]
	fat = fat[0]
	# print('Fat:', fat)
	# print('\n')

	carbs = tree.xpath('//span[@itemprop="carbohydrateContent"]/text()')
	carbs = carbs[0]
	# print('Carbs:', carbs)
	# print('\n')

	protein = tree.xpath('//span[@itemprop="proteinContent"]/text()')
	protein = [proteins.replace(' ', '') for proteins in protein]
	protein = protein[0]
	# print('Protein:', protein)
	# print('\n')

	cholesterol = tree.xpath('//span[@itemprop="cholesterolContent"]/text()')
	cholesterol = [chole.replace(' ', '') for chole in cholesterol]
	cholesterol = cholesterol[0]
	# print('Cholesterol:', cholesterol)
	# print('\n')

	sodium = tree.xpath('//span[@itemprop="sodiumContent"]/text()')
	sodium = [sod.replace(' ', '') for sod in sodium]
	sodium = sodium[0]
	# print('Sodium:', sodium)
	# print('\n')

	return [images, foodCategories, servings, titles, ingredients, instructions, cookTime, calorieCount, fat, carbs, protein, cholesterol, sodium]
	# browser = webdriver.Chrome(driverLocation)
	# browser.get('https://www.allrecipes.com/recipe/238506/marsala-marinated-skirt-steak/?internalSource=hub%20recipe&referringContentType=Search')

	# elm = browser.find_element_by_link_text('Full nutrition')
	# browser.implicitly_wait(5)
	# elm.click()

def scrapeTest(contents):
	print("\nBEGIN UNIT TEST\n")
	if(contents[0][0] == 'https://images.media-allrecipes.com/userphotos/560x315/49064.jpg'):
		print("Subtest Passed: Correct jpg scraped")
	else:
		print("Subtest Failed: Incorrect jpg")
	if(contents[1][0] == 'Salad'):
		print("Subtest Passed: Correct category scraped")
	else:
		print("Subtest Failed: Incorrect category")
	if(contents[2][0] == '6'): 
		print("Subtest Passed: Correct servings scraped")
	else:
		print("Subtest Failed: Incorrect servings")
	if(contents[3][0] == 'Roquefort Pear Salad'):
		print("Subtest Passed: Correct title scraped")
	else:
		print("Subtest Failed: Incorrect title")
	if(contents[4][0] == '1 head leaf lettuce, torn into bite-size pieces' 
		and contents[4][1] == '3 pears - peeled, cored and chopped'
		and contents[4][2] == '5 ounces Roquefort cheese, crumbled'
		and contents[4][3] == '1 avocado - peeled, pitted, and diced'
		and contents[4][4] == '1/2 cup thinly sliced green onions'
		and contents[4][5] == '1/4 cup white sugar'
		and contents[4][6] == '1/2 cup pecans'
		and contents[4][7] == '1/3 cup olive oil'
		and contents[4][8] == '3 tablespoons red wine vinegar'
		and contents[4][9] == '1 1/2 teaspoons white sugar'
		and contents[4][10] == '1 1/2 teaspoons prepared mustard'
		and contents[4][11] == '1 clove garlic, chopped'
		and contents[4][12] == '1/2 teaspoon salt'
		and contents[4][13] == 'fresh ground black pepper to taste'):
		print("Subtest Passed: Correct ingredients scraped")
	else:
		print("Subtest Failed: Incorrect ingredients")
	if(contents[5][0] == 'In a skillet over medium heat, stir 1/4 cup of sugar together with the pecans. Continue stirring gently until sugar has melted and caramelized the pecans. Carefully transfer nuts onto waxed paper. Allow to cool, and break into pieces.'
		and contents[5][1] == 'For the dressing, blend oil, vinegar, 1 1/2 teaspoons sugar, mustard, chopped garlic, salt, and pepper.'
		and contents[5][2] == 'In a large serving bowl, layer lettuce, pears, blue cheese, avocado, and green onions. Pour dressing over salad, sprinkle with pecans, and serve.'):
		print("Subtest Passed: Correct Instructions scraped")
	else:
		print("Subtest Failed: Incorrect instructions")
	if(contents[6] == '30'):
		print("Subtest Passed: Correct cooking time scraped")
	else: 
		print("Subtest Failed: Incorrect cooking time")
	if(contents[7] == '426'):
		print("Subtest Passed: Correct Calorie count scraped")
	else:
		print("Subtest Failed: Incorrect calorie count")
	if(contents[8] == '31.6'):
		print("Subtest Passed: Correct Fat content scraped")
	else:
		print("Subtest Failed: Incorrect Fat content")
	if(contents[9] == '33.1'):
		print("Subtest Passed: Correct Carbs content scraped")
	else:
		print("Subtest Failed: Incorrect Carbs content")
	if(contents[10] == '8'):
		print("Subtest Passed: Correct Protein content scraped")
	else:
		print("Subtest Failed: Incorrect Protein content")
	if(contents[11] == '21'):
		print("Subtest Passed: Correct Cholesterol content scraped")
	else:
		print("Subtest Failed: Incorrect Cholesterol contents")
	if(contents[12] == '654'):
		print("Subtest Passed: Correct Sodium content scraped")
	else: 
		print("Subtest Failed: Incorrect Sodium content")

	print("\nEND UNIT TEST\n")


def main():
	pageContents = scrape() 
	scrapeTest(pageContents)

if __name__ == "__main__" :
	main()



