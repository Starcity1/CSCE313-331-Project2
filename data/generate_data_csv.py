import csv
import random
from datetime import date, timedelta

def generate_dates(year):
    start_date = date(year, 1, 1)
    end_date = date(year, 12, 31)

    days_of_year = []

    current_date = start_date
    while current_date <= end_date:
        formatted_date = current_date.strftime("%Y-%m-%d")
        days_of_year.append(formatted_date)
        current_date += timedelta(days=1)

    return days_of_year

sizes_3 = ["small", "medium", "large"]
sizes_2 = ["medium", "large"]
temperatures = ["hot", "cold"]
ice_levels = ["regular", "less", "no ice"]
sugar_levels = [0,30,50,70,100,120]

drinks = {
"classic": {
    "KF Black Tea": [3.25,4.00,4.00], 
    "KF Green Tea": [3.25,4.00,4.00], 
    "KF Oolong Tea": [3.25,4.00,4.00], 
    "KF Honey Tea": [3.25,4.00,4.25], 
    "Longan Jujube Tea": [3.25,4.00,4.25]
},

"espresso": {
    "Signature Coffee":[3.25,4.25,4.50],
    "Caramel Macchiato":[3.25,4.25,4.50],
    "Mocha":[3.25,4.25,4.50],
    "Latte":[3.25,4.25,4.50]
},

"milk": {
    "Thai Milk Tea":[3.50,4.50,4.50],
    "Taro Milk Tea":[3.50,4.50,4.50],
    "Coconut Milk Tea":[3.50,4.50,4.50],
    "Almond Milk Tea":[3.50,4.50,4.50],
    "winter Melon Milk Green Tea":[3.50,4.50,4.50]
},

"slush": {
    "Mango Slush": [4.50,5.50],
    "Taro Slush": [4.50,5.50],
    "Oreo Slush": [4.50,5.50],
    "Red Bean Slush": [4.50,5.50]
},


"yogurt": {
    "Yogurt Orange":[4.50,6.00],
    "Yogurt Grapefruit":[4.50,6.00]
}
}

topping_names = {
    "Brown Sugar Wow": 0.5, 
    "Bubble": 0.5, 
    "Mango Jelly": 0.5, 
    "Aloe Jelly": 0.75, 
    "Nata Jelly": 0.5, 
    "Herbal Jelly": 0.5, 
    "Purple Yam": 0.75, 
    "Milk Cap": 1.00, 
    "Berry Crystal Bubble": 0.75
}

merchandise_names = {
    "Green Tea": 14.95
}


order_attributes = [
    "orderID",
    "empID",
    "date",
    "time",
    "total",
    "tip"
]

drink_attributes = [
    "drinkID",
    "orderID",
    "name",
    "category",
    "size",
    "temp",
    "ice_level",
    "sugar_level",
    "price"
]

topping = [
    "toppingID",
    "drinkID",
    "name",
    "quantity"
]

employee_attributes = [
    "empID",
    "fname",
    "lname",
    "title"
]


def generate_times():
    # More weight given to times from 6pm onwards
    hours = [11, 12, 13, 14, 15, 16, 17, 18, 18, 18, 19, 19, 19, 20, 20, 20, 21, 21, 21, 22]
    sales_data = []
    random_num = random.randint(1,365)
    is_peak_day = (1 == random_num or 365 == random_num)

    if is_peak_day:
        print("Peak Day" )
        for _ in range(random.randint(200, 300)):  
            hour = random.choice(hours)
            minute = random.randint(0, 59)
            second = random.randint(0, 59)
            
            time_string = f"{hour:02}:{minute:02}:{second:02}"
            sales_data.append(time_string)

    else:
        for _ in range(random.randint(90, 190)):  
            hour = random.choice(hours)
            minute = random.randint(0, 59)
            second = random.randint(0, 59)
            
            time_string = f"{hour:02}:{minute:02}:{second:02}"
            sales_data.append(time_string)
        
    return sorted(sales_data)  # sorted to make it chronological

year_2024_dates = generate_dates(2022)
sales_data_per_day = {}

for d in year_2024_dates:
    sales_data_per_day[d] = generate_times()


item_probabilities = [1,1,1,1,1,1,1,2,2,2,2,2,3,3,3,4,5,6]




merchandise_entity = {
    "merchID": [],
    "orderID": [],
    "name": [],
    "price": []
}
order_entity = {
    "orderID": [],
    "empID": [],
    "date": [],
    "time": [],
    "total": [],
    "tip": []
}

drink_entity = {
    "drinkID": [],
    "orderID": [],
    "name": [],
    "category": [],
    "size": [],
    "temp": [],
    "ice_level": [],
    "sugar_level": [],
    "price": []
}



topping_entity = {
    "toppingID": [],
    "drinkID": [],
    "name": [],
    "quantity": [],
    "price": []
}

employee_entity = {
    "empID": [1000, 2000, 3000, 4000],
    "fname": ["Peyton", "David", "Mikey", "Jaejin"],
    "lname": ["Smith", "Rodriguez Sanchez", "Morris", "Cha"],
    "title": ["Cashier", "Cashier", "Cashier", "Manager"]
}

order_counter = 1000000
drink_counter = 1000000
topping_counter = 1000000
merchandise_counter = 1000000

def generate_order_id():
    global order_counter
    order_counter += 1
    return order_counter

def generate_drink_id():
    global drink_counter
    drink_counter += 1
    return drink_counter

def generate_topping_id():
    global topping_counter
    topping_counter += 1
    return topping_counter

def generate_merchandise_id():
    global merchandise_counter
    merchandise_counter += 1
    return merchandise_counter

def insert_row_topping(topping_entity, toppingID, drinkID, name, quantity, price):
    topping_entity["toppingID"].append(toppingID)
    topping_entity["drinkID"].append(drinkID)
    topping_entity["name"].append(name)
    topping_entity["quantity"].append(quantity)
    topping_entity["price"].append(price)

def insert_row_drink(drink_entity, drinkID, orderID,  name, category, size, temp, iceLevel, sugarLevel, price):
    drink_entity["drinkID"].append(drinkID)
    drink_entity["orderID"].append(orderID)
    drink_entity["name"].append(name)
    drink_entity["temp"].append(temp)
    drink_entity["category"].append(category)
    drink_entity["size"].append(size)
    drink_entity["ice_level"].append(iceLevel)
    drink_entity["sugar_level"].append(sugarLevel)
    drink_entity["price"].append(price)

def insert_row_order(orderEntity, orderID, empID, date, time, total, tip):
    orderEntity["orderID"].append(orderID)
    orderEntity["empID"].append(empID)
    orderEntity["date"].append(date)
    orderEntity["time"].append(time)
    orderEntity["total"].append(total)
    orderEntity["tip"].append(tip)

def insert_row_merch(merchEntity, merchID, orderID, name, price):
    merchEntity["orderID"].append(orderID)
    merchEntity["merchID"].append(merchID)
    merchEntity["price"].append(price)
    merchEntity["name"].append(name)

for day, times in sales_data_per_day.items():
    for t in times:
        order_id = generate_order_id()
        num_items = random.choice(item_probabilities)
        running_total = 0.0
        for item in range(num_items):
            drink_id = generate_drink_id()
            category = random.choice(list(drinks.items()))

            drink = random.choice(list(category[1].items()))
            price = random.choice(drink[1])
            size_int = drink[1].index(price)
            running_total += price
            size = ''
            if(len(drink[1]) ==2):
                size = sizes_2[size_int]
            else:
                size = sizes_3[size_int]

            insert_row_drink(drink_entity,drink_id,order_id, drink[0],category[0], size, random.choice(temperatures), random.choice(ice_levels), random.choice(sugar_levels), price)
            toppings_amount = random.randint(0, 3)
            for top in range(toppings_amount):
                topping_id = generate_topping_id()
                topping_name = random.choice(list(topping_names.items()))
                price = topping_name[1]
                quantity = random.randint(1, 2)
                insert_row_topping(topping_entity, topping_id, drink_id, topping_name[0], quantity, price * quantity)
                running_total += (price * quantity)
        
        if(random.randint(0,30) == 10):
            merch_id = generate_merchandise_id()
            merch = random.choice(list(merchandise_names.items()))
            name = merch[0]
            price = merch[1]
            insert_row_merch(merchandise_entity, merch_id, order_id, name, price)
            running_total += price


        insert_row_order(order_entity, order_id, random.choice(employee_entity["empID"]),day,t,running_total, round((random.randint(0, 15)/100) * running_total, 2))

# Convert the dictionary to a CSV file
with open('drink_entity.csv', 'w', newline='') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=drink_entity.keys())
    
    writer.writeheader()
    for i in range(len(drink_entity["drinkID"])):
        row = {key: drink_entity[key][i] for key in drink_entity.keys()}
        writer.writerow(row)


# Convert the dictionary to a CSV file
with open('topping_entity.csv', 'w', newline='') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=topping_entity.keys())
    
    writer.writeheader()
    for i in range(len(topping_entity["toppingID"])):
        row = {key: topping_entity[key][i] for key in topping_entity.keys()}
        writer.writerow(row)


# Convert the dictionary to a CSV file
with open('order_entity.csv', 'w', newline='') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=order_entity.keys())
    
    writer.writeheader()
    for i in range(len(order_entity["orderID"])):
        row = {key: order_entity[key][i] for key in order_entity.keys()}
        writer.writerow(row)

# Convert the dictionary to a CSV file
with open('merchandise_entity.csv', 'w', newline='') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=merchandise_entity.keys())
    
    writer.writeheader()
    for i in range(len(merchandise_entity["merchID"])):
        row = {key: merchandise_entity[key][i] for key in merchandise_entity.keys()}
        writer.writerow(row)

# Convert the dictionary to a CSV file
with open('employee_entity.csv', 'w', newline='') as csvfile:
    writer = csv.DictWriter(csvfile, fieldnames=employee_entity.keys())
    
    writer.writeheader()
    for i in range(len(employee_entity["empID"])):
        row = {key: employee_entity[key][i] for key in employee_entity.keys()}
        writer.writerow(row)

print("CSV file created successfully!")


