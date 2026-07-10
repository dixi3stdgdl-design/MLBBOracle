#!/usr/bin/env python3
"""
Synthetic Training Data Generator for MLBB Oracle
Generates hero portraits, item icons, and gold/damage number images
"""

import os
import random
import json
from PIL import Image, ImageDraw, ImageFont, ImageFilter
import numpy as np

# Configuration
OUTPUT_DIR = "training_data"
IMG_SIZE = 64
HEROES_PER_CLASS = 50
ITEMS_PER_CLASS = 50
NUMBERS_PER_CLASS = 200

# Hero labels
HEROES = [
    "Miya", "Layla", "Bruno", "Wanwan", "Claude", "Karrie", "Granger", "Irithel",
    "Lesley", "Moskov", "Hanabi", "Kimmy", "Beatrix", "Fanny", "Gusion", "Lancelot",
    "Hayabusa", "Benedetta", "Ling", "Hanzo", "Natalia", "Saber", "Helcurt", "Alucard",
    "Zilong", "Chou", "Aldous", "Yu Zhong", "Esmeralda", "Paquito", "Thamuz", "Terizla",
    "X.Borg", "Badang", "Martis", "Alpha", "Leomord", "Kagura", "Harley", "Valir",
    "Lunox", "Change", "Kadita", "Pharsa", "Eudora", "Aurora", "Vexana", "Cyclops",
    "Gord", "Tigreal", "Johnson", "Khufra", "Atlas", "Grock", "Akai", "Franco",
    "Minotaur", "Hylos", "Uranus", "Baxia", "Belerick", "Angela", "Estes", "Rafaela",
    "Diggie", "Mathilda", "Floryn"
]

# Item labels
ITEMS = [
    "Blade of Despair", "Berserker's Fury", "Windtalker", "Demon Hunter Sword",
    "Scarlet Phantom", "Bloodlust Axe", "Endless Battle", "Thunder Belt",
    "Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand",
    "Ice Queen Wand", "Concentrated Energy", "Necklace of Durance", "Immortality",
    "Athena's Shield", "Radiant Armor", "Antique Cuirass", "Dominance Ice",
    "Twilight Armor", "Blade Armor", "Queen's Wings", "Swift Boots",
    "Warrior Boots", "Magic Shoes", "Arcane Boots", "Tough Boots",
    "Demon Shoes", "Rapid Boots", "Boots of Tranquility", "Hunter Knife",
    "Retribution", "Ice Retribution", "Flame Retribution", "Mask",
    "Conceal", "Encourage", "Favor", "Dire Hit"
]

# Role categories for heroes
ROLES = {
    "marksman": ["Miya", "Layla", "Bruno", "Wanwan", "Claude", "Karrie", "Granger",
                 "Irithel", "Lesley", "Moskov", "Hanabi", "Kimmy", "Beatrix"],
    "assassin": ["Fanny", "Gusion", "Lancelot", "Hayabusa", "Benedetta", "Ling",
                 "Hanzo", "Natalia", "Saber", "Helcurt"],
    "fighter": ["Alucard", "Zilong", "Chou", "Aldous", "Yu Zhong", "Esmeralda",
                "Paquito", "Thamuz", "Terizla", "X.Borg", "Badang", "Martis",
                "Alpha", "Leomord"],
    "mage": ["Kagura", "Harley", "Valir", "Lunox", "Change", "Kadita", "Pharsa",
             "Eudora", "Aurora", "Vexana", "Cyclops", "Gord"],
    "tank": ["Tigreal", "Johnson", "Khufra", "Atlas", "Grock", "Akai", "Franco",
             "Minotaur", "Hylos", "Uranus", "Baxia", "Belerick"],
    "support": ["Angela", "Estes", "Rafaela", "Diggie", "Mathilda", "Floryn"]
}

# Item categories
ITEM_CATEGORIES = {
    "weapon": ["Blade of Despair", "Berserker's Fury", "Windtalker", "Demon Hunter Sword",
               "Scarlet Phantom", "Bloodlust Axe", "Endless Battle", "Thunder Belt"],
    "magic": ["Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand",
              "Ice Queen Wand", "Concentrated Energy", "Necklace of Durance"],
    "defense": ["Immortality", "Athena's Shield", "Radiant Armor", "Antique Cuirass",
                "Dominance Ice", "Twilight Armor", "Blade Armor", "Queen's Wings"],
    "boots": ["Swift Boots", "Warrior Boots", "Magic Shoes", "Arcane Boots",
              "Tough Boots", "Demon Shoes", "Rapid Boots", "Boots of Tranquility"],
    "jungle": ["Hunter Knife", "Retribution", "Ice Retribution", "Flame Retribution",
               "Mask", "Conceal", "Encourage", "Favor", "Dire Hit"]
}

# Color schemes
ROLE_COLORS = {
    "marksman": [(255, 100, 100), (200, 50, 50)],  # Red tones
    "assassin": [(150, 50, 200), (100, 0, 150)],   # Purple tones
    "fighter": [(200, 150, 50), (150, 100, 0)],    # Orange/Brown
    "mage": [(50, 100, 255), (0, 50, 200)],        # Blue tones
    "tank": [(100, 180, 100), (50, 130, 50)],      # Green tones
    "support": [(255, 200, 100), (200, 150, 50)]   # Yellow/Gold
}

ITEM_CATEGORY_COLORS = {
    "weapon": [(220, 50, 50), (180, 30, 30)],      # Red
    "magic": [(100, 50, 220), (60, 20, 180)],      # Purple
    "defense": [(50, 150, 200), (30, 100, 160)],   # Blue
    "boots": [(50, 200, 100), (30, 160, 60)],      # Green
    "jungle": [(200, 150, 50), (160, 110, 20)]     # Yellow/Brown
}

# Role icons (simple shapes)
ROLE_ICONS = {
    "marksman": "crosshair",
    "assassin": "dagger",
    "fighter": "fist",
    "mage": "star",
    "tank": "shield",
    "support": "heart"
}

def get_hero_role(hero_name):
    """Get the role for a hero"""
    for role, heroes in ROLES.items():
        if hero_name in heroes:
            return role
    return "fighter"

def get_item_category(item_name):
    """Get the category for an item"""
    for category, items in ITEM_CATEGORIES.items():
        if item_name in items:
            return category
    return "weapon"

def draw_role_icon(draw, role, x, y, size):
    """Draw a simple icon based on role"""
    if role == "marksman":
        # Crosshair
        draw.ellipse([x, y, x+size, y+size], outline="white", width=2)
        draw.line([x+size//2, y, x+size//2, y+size], fill="white", width=2)
        draw.line([x, y+size//2, x+size, y+size//2], fill="white", width=2)
    elif role == "assassin":
        # Dagger (triangle)
        points = [(x+size//2, y), (x, y+size), (x+size, y+size)]
        draw.polygon(points, fill="white")
    elif role == "fighter":
        # Fist (circle with line)
        draw.ellipse([x+size//4, y, x+3*size//4, y+3*size//4], outline="white", width=2)
        draw.line([x+size//2, y+3*size//4, x+size//2, y+size], fill="white", width=3)
    elif role == "mage":
        # Star
        points = []
        for i in range(5):
            angle = i * 72 - 90
            px = x + size//2 + int(size//2 * 0.5 * np.cos(np.radians(angle)))
            py = y + size//2 + int(size//2 * 0.5 * np.sin(np.radians(angle)))
            points.append((px, py))
            angle += 36
            px = x + size//2 + int(size//2 * 0.2 * np.cos(np.radians(angle)))
            py = y + size//2 + int(size//2 * 0.2 * np.sin(np.radians(angle)))
            points.append((px, py))
        draw.polygon(points, fill="white")
    elif role == "tank":
        # Shield
        points = [(x+size//2, y), (x, y+size//2), (x+size//4, y+size),
                  (x+3*size//4, y+size), (x+size, y+size//2)]
        draw.polygon(points, fill="white")
    elif role == "support":
        # Heart (two circles and triangle)
        draw.ellipse([x, y+size//4, x+size//2, y+3*size//4], fill="white")
        draw.ellipse([x+size//2, y+size//4, x+size, y+3*size//4], fill="white")
        points = [(x, y+size//2), (x+size//2, y+size), (x+size, y+size//2)]
        draw.polygon(points, fill="white")

def draw_item_shape(draw, category, x, y, size):
    """Draw a shape based on item category"""
    if category == "weapon":
        # Sword shape
        draw.rectangle([x+size//3, y, x+2*size//3, y+2*size//3], fill="white")
        draw.rectangle([x+size//4, y+2*size//3, x+3*size//4, y+size], fill="white")
    elif category == "magic":
        # Circle with sparkles
        draw.ellipse([x, y, x+size, y+size], outline="white", width=2)
        for i in range(4):
            cx = x + size//4 + (i % 2) * size//2
            cy = y + size//4 + (i // 2) * size//2
            draw.ellipse([cx-2, cy-2, cx+2, cy+2], fill="white")
    elif category == "defense":
        # Shield
        points = [(x+size//2, y), (x, y+size//3), (x+size//4, y+size),
                  (x+3*size//4, y+size), (x+size, y+size//3)]
        draw.polygon(points, fill="white")
    elif category == "boots":
        # Boot shape
        draw.rectangle([x+size//4, y, x+3*size//4, y+2*size//3], fill="white")
        draw.rectangle([x+size//6, y+2*size//3, x+5*size//6, y+size], fill="white")
    elif category == "jungle":
        # Diamond shape
        points = [(x+size//2, y), (x+size, y+size//2), (x+size//2, y+size), (x, y+size//2)]
        draw.polygon(points, fill="white")

def add_noise(img, intensity=25):
    """Add random noise to image"""
    arr = np.array(img)
    noise = np.random.normal(0, intensity, arr.shape)
    arr = np.clip(arr + noise, 0, 255).astype(np.uint8)
    return Image.fromarray(arr)

def augment_image(img):
    """Apply random augmentations"""
    # Random rotation
    if random.random() > 0.5:
        angle = random.uniform(-15, 15)
        img = img.rotate(angle, fillcolor=(0, 0, 0))

    # Random brightness
    if random.random() > 0.5:
        factor = random.uniform(0.7, 1.3)
        img = Image.fromarray(np.clip(np.array(img) * factor, 0, 255).astype(np.uint8))

    # Random noise
    if random.random() > 0.5:
        img = add_noise(img, intensity=random.randint(5, 20))

    # Random blur
    if random.random() > 0.7:
        img = img.filter(ImageFilter.GaussianBlur(radius=random.uniform(0.5, 1.5)))

    return img

def generate_hero_portrait(hero_name, output_path):
    """Generate a synthetic hero portrait image"""
    role = get_hero_role(hero_name)
    colors = ROLE_COLORS[role]

    # Create image with gradient background
    img = Image.new('RGB', (IMG_SIZE, IMG_SIZE), colors[0])
    draw = ImageDraw.Draw(img)

    # Draw gradient
    for y in range(IMG_SIZE):
        ratio = y / IMG_SIZE
        r = int(colors[0][0] * (1 - ratio) + colors[1][0] * ratio)
        g = int(colors[0][1] * (1 - ratio) + colors[1][1] * ratio)
        b = int(colors[0][2] * (1 - ratio) + colors[1][2] * ratio)
        draw.line([(0, y), (IMG_SIZE, y)], fill=(r, g, b))

    # Draw role icon in top-right corner
    draw_role_icon(draw, role, IMG_SIZE - 20, 5, 15)

    # Draw hero name
    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", 8)
    except:
        font = ImageFont.load_default()

    # Center the text
    text_bbox = draw.textbbox((0, 0), hero_name, font=font)
    text_width = text_bbox[2] - text_bbox[0]
    text_x = (IMG_SIZE - text_width) // 2
    text_y = IMG_SIZE - 15

    # Draw text shadow and text
    draw.text((text_x + 1, text_y + 1), hero_name, fill="black", font=font)
    draw.text((text_x, text_y), hero_name, fill="white", font=font)

    # Add some decorative elements
    for _ in range(random.randint(2, 5)):
        x = random.randint(0, IMG_SIZE - 10)
        y = random.randint(0, IMG_SIZE - 20)
        size = random.randint(3, 8)
        draw.ellipse([x, y, x + size, y + size], fill=(255, 255, 255, 128))

    # Apply augmentations
    img = augment_image(img)

    img.save(output_path)

def generate_item_icon(item_name, output_path):
    """Generate a synthetic item icon image"""
    category = get_item_category(item_name)
    colors = ITEM_CATEGORY_COLORS[category]

    # Create image with solid background
    img = Image.new('RGB', (IMG_SIZE, IMG_SIZE), (30, 30, 40))
    draw = ImageDraw.Draw(img)

    # Draw item shape
    margin = 10
    draw_item_shape(draw, category, margin, margin, IMG_SIZE - 2 * margin - 20)

    # Draw category color accent
    accent_x = IMG_SIZE - 12
    draw.rectangle([accent_x, 5, accent_x + 7, 15], fill=colors[0])

    # Draw item name (abbreviated)
    try:
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf", 6)
    except:
        font = ImageFont.load_default()

    # Use abbreviated name
    short_name = item_name[:8] if len(item_name) > 8 else item_name
    text_bbox = draw.textbbox((0, 0), short_name, font=font)
    text_width = text_bbox[2] - text_bbox[0]
    text_x = (IMG_SIZE - text_width) // 2
    text_y = IMG_SIZE - 12

    draw.text((text_x, text_y), short_name, fill="white", font=font)

    # Apply augmentations
    img = augment_image(img)

    img.save(output_path)

def generate_gold_number(number, output_path):
    """Generate a synthetic gold/number image"""
    # Create dark background
    img = Image.new('RGB', (IMG_SIZE, IMG_SIZE), (20, 20, 30))
    draw = ImageDraw.Draw(img)

    # Draw number text
    try:
        font_size = random.choice([16, 20, 24, 28])
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", font_size)
    except:
        font = ImageFont.load_default()

    text = str(number)
    text_bbox = draw.textbbox((0, 0), text, font=font)
    text_width = text_bbox[2] - text_bbox[0]
    text_height = text_bbox[3] - text_bbox[1]

    # Center the text
    text_x = (IMG_SIZE - text_width) // 2 + random.randint(-5, 5)
    text_y = (IMG_SIZE - text_height) // 2 + random.randint(-5, 5)

    # Draw gold coin icon
    coin_x = text_x - 12
    coin_y = text_y + text_height // 2 - 5
    draw.ellipse([coin_x, coin_y, coin_x + 10, coin_y + 10], fill=(255, 215, 0))
    draw.ellipse([coin_x + 2, coin_y + 2, coin_x + 8, coin_y + 8], fill=(255, 235, 100))

    # Draw text with gold color
    draw.text((text_x, text_y), text, fill=(255, 215, 0), font=font)

    # Add glow effect
    for i in range(2):
        offset = i + 1
        draw.text((text_x - offset, text_y), text, fill=(255, 235, 100, 100), font=font)
        draw.text((text_x + offset, text_y), text, fill=(255, 235, 100, 100), font=font)

    # Apply augmentations
    img = augment_image(img)

    img.save(output_path)

def generate_damage_number(number, output_path):
    """Generate a synthetic damage number image"""
    # Create semi-transparent background
    img = Image.new('RGB', (IMG_SIZE, IMG_SIZE), (40, 10, 10))
    draw = ImageDraw.Draw(img)

    # Draw number text
    try:
        font_size = random.choice([14, 18, 22, 26])
        font = ImageFont.truetype("/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf", font_size)
    except:
        font = ImageFont.load_default()

    text = str(number)
    text_bbox = draw.textbbox((0, 0), text, font=font)
    text_width = text_bbox[2] - text_bbox[0]
    text_height = text_bbox[3] - text_bbox[1]

    # Center the text with slight random offset
    text_x = (IMG_SIZE - text_width) // 2 + random.randint(-8, 8)
    text_y = (IMG_SIZE - text_height) // 2 + random.randint(-8, 8)

    # Draw text with red/orange color (damage)
    color = random.choice([(255, 50, 50), (255, 100, 0), (255, 150, 0)])
    draw.text((text_x, text_y), text, fill=color, font=font)

    # Add impact effect
    for _ in range(random.randint(3, 6)):
        x = text_x + random.randint(-5, text_width + 5)
        y = text_y + random.randint(-5, text_height + 5)
        size = random.randint(1, 3)
        draw.ellipse([x, y, x + size, y + size], fill=(255, 200, 100))

    # Apply augmentations
    img = augment_image(img)

    img.save(output_path)

def generate_number_label(number):
    """Generate a label for number classification"""
    # Bucket numbers into ranges
    if number < 100:
        return "small"
    elif number < 1000:
        return "medium"
    elif number < 5000:
        return "large"
    else:
        return "huge"

def main():
    """Main function to generate all synthetic data"""
    print("=" * 60)
    print("MLBB Oracle - Synthetic Training Data Generator")
    print("=" * 60)

    # Create output directories
    os.makedirs(f"{OUTPUT_DIR}/heroes", exist_ok=True)
    os.makedirs(f"{OUTPUT_DIR}/items", exist_ok=True)
    os.makedirs(f"{OUTPUT_DIR}/numbers", exist_ok=True)

    labels = {"heroes": {}, "items": {}, "numbers": {}}

    # Generate hero portraits
    print("\n[1/3] Generating hero portraits...")
    for i, hero in enumerate(HEROES):
        hero_dir = f"{OUTPUT_DIR}/heroes/{hero.replace(' ', '_').replace('.', '')}"
        os.makedirs(hero_dir, exist_ok=True)
        labels["heroes"][hero] = i

        for j in range(HEROES_PER_CLASS):
            img_path = f"{hero_dir}/hero_{j:04d}.png"
            generate_hero_portrait(hero, img_path)

        print(f"  [{i+1}/{len(HEROES)}] Generated {HEROES_PER_CLASS} images for {hero}")

    # Generate item icons
    print("\n[2/3] Generating item icons...")
    for i, item in enumerate(ITEMS):
        item_dir = f"{OUTPUT_DIR}/items/{item.replace(' ', '_').replace('\'', '')}"
        os.makedirs(item_dir, exist_ok=True)
        labels["items"][item] = i

        for j in range(ITEMS_PER_CLASS):
            img_path = f"{item_dir}/item_{j:04d}.png"
            generate_item_icon(item, img_path)

        print(f"  [{i+1}/{len(ITEMS)}] Generated {ITEMS_PER_CLASS} images for {item}")

    # Generate gold/damage numbers
    print("\n[3/3] Generating number images...")
    number_ranges = [
        (10, 99),       # Small numbers
        (100, 999),     # Medium numbers
        (1000, 4999),   # Large numbers
        (5000, 99999),  # Huge numbers
    ]

    for range_idx, (min_val, max_val) in enumerate(number_ranges):
        range_dir = f"{OUTPUT_DIR}/numbers/{generate_number_label(min_val)}"
        os.makedirs(range_dir, exist_ok=True)
        labels["numbers"][generate_number_label(min_val)] = range_idx

        for j in range(NUMBERS_PER_CLASS):
            number = random.randint(min_val, max_val)

            # Generate both gold and damage versions
            gold_path = f"{range_dir}/gold_{j:04d}.png"
            damage_path = f"{range_dir}/damage_{j:04d}.png"

            generate_gold_number(number, gold_path)
            generate_damage_number(number, damage_path)

        print(f"  [{range_idx+1}/{len(number_ranges)}] Generated {NUMBERS_PER_CLASS * 2} number images")

    # Save labels
    print("\nSaving labels...")
    with open(f"{OUTPUT_DIR}/hero_labels.json", "w") as f:
        json.dump(labels["heroes"], f, indent=2)

    with open(f"{OUTPUT_DIR}/item_labels.json", "w") as f:
        json.dump(labels["items"], f, indent=2)

    with open(f"{OUTPUT_DIR}/number_labels.json", "w") as f:
        json.dump(labels["numbers"], f, indent=2)

    # Save label lists
    with open(f"{OUTPUT_DIR}/hero_labels.txt", "w") as f:
        for hero in HEROES:
            f.write(f"{hero}\n")

    with open(f"{OUTPUT_DIR}/item_labels.txt", "w") as f:
        for item in ITEMS:
            f.write(f"{item}\n")

    with open(f"{OUTPUT_DIR}/number_labels.txt", "w") as f:
        for label in ["small", "medium", "large", "huge"]:
            f.write(f"{label}\n")

    # Print summary
    hero_count = len(HEROES) * HEROES_PER_CLASS
    item_count = len(ITEMS) * ITEMS_PER_CLASS
    number_count = len(number_ranges) * NUMBERS_PER_CLASS * 2

    print("\n" + "=" * 60)
    print("Generation Complete!")
    print("=" * 60)
    print(f"Hero portraits:   {hero_count:,} images ({len(HEROES)} classes)")
    print(f"Item icons:       {item_count:,} images ({len(ITEMS)} classes)")
    print(f"Number images:    {number_count:,} images (4 classes)")
    print(f"Total images:     {hero_count + item_count + number_count:,}")
    print(f"\nOutput directory: {OUTPUT_DIR}/")
    print("=" * 60)

if __name__ == "__main__":
    main()
