package com.mlbb.oracle.data

data class Equipment(
    val name: String,
    val shortName: String,
    val category: EquipmentCategory,
    val price: Int,
    val stats: Map<String, Int>,
    val passive: String,
    val passiveEffect: String,
    val counterFor: List<String>,
    val situational: Boolean = false
)

enum class EquipmentCategory(val displayName: String) {
    ATTACK("Attack"),
    MAGIC("Magic"),
    DEFENSE("Defense"),
    MOVEMENT("Movement"),
    JUNGLE("Jungle"),
    ROAM("Roam")
}

object EquipmentDatabase {
    private val equipment = listOf(
        // ==================== ATTACK ITEMS ====================
        Equipment(
            name = "Blade of Despair", shortName = "BoD",
            category = EquipmentCategory.ATTACK, price = 3010,
            stats = mapOf("Physical Attack" to 160, "Movement Speed" to 5),
            passive = "Despair",
            passiveEffect = "Deals extra 25% damage to enemies below 50% HP",
            counterFor = listOf("Esmeralda", "Yu Zhong", "Thamuz", "X.Borg")
        ),
        Equipment(
            name = "Berserker's Fury", shortName = "BF",
            category = EquipmentCategory.ATTACK, price = 2350,
            stats = mapOf("Physical Attack" to 65, "Critical Chance" to 25),
            passive = "Critical Fury",
            passiveEffect = "Critical hits deal 200% damage",
            counterFor = listOf("Saber", "Hayabusa", "Alucard")
        ),
        Equipment(
            name = "Endless Battle", shortName = "EB",
            category = EquipmentCategory.ATTACK, price = 2470,
            stats = mapOf("Physical Attack" to 65, "HP" to 500, "Physical Lifesteal" to 10, "Mana Regen" to 25),
            passive = "Divine Justice",
            passiveEffect = "Basic attacks after skill deal true damage equal to 85% Physical Attack",
            counterFor = listOf("Chou", "Paquito", "Badang")
        ),
        Equipment(
            name = "Demon Hunter Sword", shortName = "DHS",
            category = EquipmentCategory.ATTACK, price = 2180,
            stats = mapOf("Physical Attack" to 35, "Attack Speed" to 25),
            passive = "Devour",
            passiveEffect = "Basic attacks deal 9% of target's current HP as physical damage",
            counterFor = listOf("Esmeralda", "Yu Zhong", "Thamuz", "Uranus")
        ),
        Equipment(
            name = "Hunter Strike", shortName = "HS",
            category = EquipmentCategory.ATTACK, price = 2010,
            stats = mapOf("Physical Attack" to 80, "Physical PEN" to 10),
            passive = "Hunter's Steps",
            passiveEffect = "Dealing damage 5 times grants 50% movement speed boost for 3s",
            counterFor = listOf("Fanny", "Ling", "Lancelot")
        ),
        Equipment(
            name = "Malefic Roar", shortName = "MR",
            category = EquipmentCategory.ATTACK, price = 2060,
            stats = mapOf("Physical Attack" to 60, "Physical PEN" to 35),
            passive = "Malefic Energy",
            passiveEffect = "Physical PEN increases by 0.05% for each point of enemy's physical defense",
            counterFor = listOf("Tigreal", "Atlas", "Khufra")
        ),
        Equipment(
            name = "Blade of Heptaseas", shortName = "BoH",
            category = EquipmentCategory.ATTACK, price = 1950,
            stats = mapOf("Physical Attack" to 70, "HP" to 250),
            passive = "Ambush",
            passiveEffect = "If no damage taken for 5s, next basic attack deals 160% damage and slows",
            counterFor = listOf("Natalia", "Saber", "Hayabusa")
        ),
        Equipment(
            name = "Windtalker", shortName = "WT",
            category = EquipmentCategory.ATTACK, price = 1820,
            stats = mapOf("Attack Speed" to 40, "Critical Chance" to 10, "Movement Speed" to 20),
            passive = "Typhoon",
            passiveEffect = "Basic attacks deal 100 magic damage to 3 enemies",
            counterFor = listOf("Miya", "Layla", "Irithel")
        ),
        Equipment(
            name = "Scarlet Phantom", shortName = "SP",
            category = EquipmentCategory.ATTACK, price = 2020,
            stats = mapOf("Physical Attack" to 40, "Attack Speed" to 40, "Critical Chance" to 10),
            passive = "Frenzy",
            passiveEffect = "Critical hits increase attack speed by 30% for 2s",
            counterFor = listOf("Claude", "Karrie", "Moskov")
        ),
        Equipment(
            name = "Bloodlust Axe", shortName = "BA",
            category = EquipmentCategory.ATTACK, price = 1970,
            stats = mapOf("Physical Attack" to 70, "Cooldown Reduction" to 10),
            passive = "Bloodlust",
            passiveEffect = "Skill damage heals 20% of damage dealt",
            counterFor = listOf("Chou", "Paquito", "Yu Zhong")
        ),

        // ==================== MAGIC ITEMS ====================
        Equipment(
            name = "Holy Crystal", shortName = "HC",
            category = EquipmentCategory.MAGIC, price = 2180,
            stats = mapOf("Magic Power" to 100),
            passive = "Mystic Container",
            passiveEffect = "Magic Power increases by 25% every 30s, up to 3 stacks",
            counterFor = listOf("Kagura", "Lunox", "Pharsa")
        ),
        Equipment(
            name = "Divine Glaive", shortName = "DG",
            category = EquipmentCategory.MAGIC, price = 2050,
            stats = mapOf("Magic Power" to 65, "Magic PEN" to 40),
            passive = "Divine Slash",
            passiveEffect = "Magic PEN increases by 0.1% for each point of enemy's magic defense",
            counterFor = listOf("Esmeralda", "Yu Zhong", "Thamuz")
        ),
        Equipment(
            name = "Lightning Truncheon", shortName = "LT",
            category = EquipmentCategory.MAGIC, price = 2250,
            stats = mapOf("Magic Power" to 75, "Mana" to 500),
            passive = "Resonate",
            passiveEffect = "Skills deal extra magic damage equal to 30% of current mana",
            counterFor = listOf("Cyclops", "Gord", "Harley")
        ),
        Equipment(
            name = "Glowing Wand", shortName = "GW",
            category = EquipmentCategory.MAGIC, price = 2020,
            stats = mapOf("Magic Power" to 75, "Movement Speed" to 5),
            passive = "Scorch",
            passiveEffect = "Skills burn enemies for 2.5% of their max HP over 3s",
            counterFor = listOf("Valir", "Aurora", "Eudora")
        ),
        Equipment(
            name = "Concentrated Energy", shortName = "CE",
            category = EquipmentCategory.MAGIC, price = 2020,
            stats = mapOf("Magic Power" to 70, "Magic Lifesteal" to 20),
            passive = "Magic Lifesteal",
            passiveEffect = "Magic damage heals 20% of damage dealt",
            counterFor = listOf("Lunox", "Esmeralda", "Chang'e")
        ),
        Equipment(
            name = "Necklace of Durance", shortName = "NoD",
            category = EquipmentCategory.MAGIC, price = 2110,
            stats = mapOf("Magic Power" to 65, "Magic Lifesteal" to 10, "HP" to 300),
            passive = "Life Drain",
            passiveEffect = "Reduces enemy's shield and HP regen by 50% for 3s",
            counterFor = listOf("Angela", "Estes", "Rafaela", "Floryn")
        ),

        // ==================== DEFENSE ITEMS ====================
        Equipment(
            name = "Immortality", shortName = "IM",
            category = EquipmentCategory.DEFENSE, price = 2120,
            stats = mapOf("Physical Defense" to 40, "HP" to 800),
            passive = "Immortal",
            passiveEffect = "Revives upon death with 15% HP and a shield",
            counterFor = listOf("Lesley", "Karrie", "Claude")
        ),
        Equipment(
            name = "Athena's Shield", shortName = "AS",
            category = EquipmentCategory.DEFENSE, price = 2050,
            stats = mapOf("Magic Defense" to 56, "HP" to 620),
            passive = "Shield",
            passiveEffect = "When taking magic damage, creates a shield that absorbs 35% of damage",
            counterFor = listOf("Kagura", "Lunox", "Pharsa", "Chang'e")
        ),
        Equipment(
            name = "Radiant Armor", shortName = "RA",
            category = EquipmentCategory.DEFENSE, price = 1920,
            stats = mapOf("Magic Defense" to 52, "HP" to 540),
            passive = "Holy Blessing",
            passiveEffect = "Reduces continuous magic damage by 30%",
            counterFor = listOf("Gord", "Valir", "Cyclops")
        ),
        Equipment(
            name = "Antique Cuirass", shortName = "AC",
            category = EquipmentCategory.DEFENSE, price = 2170,
            stats = mapOf("Physical Defense" to 70, "HP" to 700),
            passive = "Mental",
            passiveEffect = "When taking skill damage, reduces enemy's Physical Attack and Magic Power by 6%",
            counterFor = listOf("Fanny", "Gusion", "Ling")
        ),
        Equipment(
            name = "Dominance Ice", shortName = "DI",
            category = EquipmentCategory.DEFENSE, price = 2020,
            stats = mapOf("Physical Defense" to 70, "Mana" to 500, "Physical PEN" to 5),
            passive = "Arctic Cold",
            passiveEffect = "Reduces nearby enemies' attack speed by 30% and movement speed by 10%",
            counterFor = listOf("Miya", "Layla", "Irithel", "Claude")
        ),
        Equipment(
            name = "Blade Armor", shortName = "BlA",
            category = EquipmentCategory.DEFENSE, price = 1870,
            stats = mapOf("Physical Defense" to 70),
            passive = "Thorns",
            passiveEffect = "Reflects 25% of basic attack damage back to attacker",
            counterFor = listOf("Miya", "Layla", "Irithel", "Bruno")
        ),
        Equipment(
            name = "Twilight Armor", shortName = "TA",
            category = EquipmentCategory.DEFENSE, price = 1980,
            stats = mapOf("Physical Defense" to 50, "HP" to 700, "HP Regen" to 5),
            passive = "Twilight Blessing",
            passiveEffect = "Reduces damage from critical hits by 30%",
            counterFor = listOf("Lesley", "Granger", "Bruno")
        ),
        Equipment(
            name = "Queen's Wings", shortName = "QW",
            category = EquipmentCategory.DEFENSE, price = 2100,
            stats = mapOf("Physical Attack" to 40, "HP" to 800, "Physical Lifesteal" to 10),
            passive = "Demonize",
            passiveEffect = "When HP falls below 30%, increases lifesteal by 30% and reduces damage taken by 30%",
            counterFor = listOf("Chou", "Paquito", "Yu Zhong")
        ),

        // ==================== MOVEMENT ITEMS ====================
        Equipment(
            name = "Swift Boots", shortName = "SB",
            category = EquipmentCategory.MOVEMENT, price = 710,
            stats = mapOf("Attack Speed" to 10),
            passive = "Swift",
            passiveEffect = "Increases attack speed",
            counterFor = emptyList()
        ),
        Equipment(
            name = "Warrior Boots", shortName = "WB",
            category = EquipmentCategory.MOVEMENT, price = 720,
            stats = mapOf("Physical Defense" to 12),
            passive = "Valor",
            passiveEffect = "Increases physical defense",
            counterFor = emptyList()
        ),
        Equipment(
            name = "Magic Shoes", shortName = "MS",
            category = EquipmentCategory.MOVEMENT, price = 700,
            stats = mapOf("Cooldown Reduction" to 10),
            passive = "Magic Shoes",
            passiveEffect = "Reduces skill cooldowns",
            counterFor = emptyList()
        ),
        Equipment(
            name = "Tough Boots", shortName = "TB",
            category = EquipmentCategory.MOVEMENT, price = 750,
            stats = mapOf("Magic Defense" to 22),
            passive = "Tenacity",
            passiveEffect = "Reduces crowd control duration by 30%",
            counterFor = emptyList()
        ),
        Equipment(
            name = "Arcane Boots", shortName = "AB",
            category = EquipmentCategory.MOVEMENT, price = 690,
            stats = mapOf("Magic PEN" to 10),
            passive = "Arcane",
            passiveEffect = "Increases magic penetration",
            counterFor = emptyList()
        ),
        Equipment(
            name = "Rapid Boots", shortName = "RB",
            category = EquipmentCategory.MOVEMENT, price = 750,
            stats = mapOf("Movement Speed" to 40),
            passive = "Rapid",
            passiveEffect = "Increases out-of-combat movement speed",
            counterFor = emptyList()
        ),

        // ==================== JUNGLE ITEMS ====================
        Equipment(
            name = "Ice Retribution", shortName = "IR",
            category = EquipmentCategory.JUNGLE, price = 0,
            stats = mapOf("Physical Attack" to 20, "Magic Power" to 20),
            passive = "Ice Retribution",
            passiveEffect = "Deals true damage to jungle monsters and steals movement speed",
            counterFor = emptyList(), situational = true
        ),
        Equipment(
            name = "Flame Retribution", shortName = "FR",
            category = EquipmentCategory.JUNGLE, price = 0,
            stats = mapOf("Physical Attack" to 30),
            passive = "Flame Retribution",
            passiveEffect = "Deals true damage to jungle monsters and steals attack",
            counterFor = emptyList(), situational = true
        ),

        // ==================== ROAM ITEMS ====================
        Equipment(
            name = "Conceal", shortName = "Con",
            category = EquipmentCategory.ROAM, price = 0,
            stats = mapOf("HP" to 500),
            passive = "Conceal",
            passiveEffect = "Nearby allies gain invisibility for 3s when entering bush",
            counterFor = emptyList(), situational = true
        ),
        Equipment(
            name = "Encourage", shortName = "Enc",
            category = EquipmentCategory.ROAM, price = 0,
            stats = mapOf("HP" to 500),
            passive = "Encourage",
            passiveEffect = "Nearby allies gain 30 Physical Attack and 30 Magic Power",
            counterFor = emptyList(), situational = true
        ),
        Equipment(
            name = "Favor", shortName = "Fav",
            category = EquipmentCategory.ROAM, price = 0,
            stats = mapOf("HP" to 500),
            passive = "Favor",
            passiveEffect = "Healing or shielding allies restores additional HP",
            counterFor = emptyList(), situational = true
        ),
        Equipment(
            name = "Dire Hit", shortName = "DH",
            category = EquipmentCategory.ROAM, price = 0,
            stats = mapOf("HP" to 500),
            passive = "Dire Hit",
            passiveEffect = "Basic attacks against enemies below 30% HP deal extra true damage",
            counterFor = emptyList(), situational = true
        )
    )

    fun getAllEquipment(): List<Equipment> = equipment

    fun getEquipmentByCategory(category: EquipmentCategory): List<Equipment> =
        equipment.filter { it.category == category }

    fun getEquipmentByName(name: String): Equipment? =
        equipment.find { it.name.equals(name, ignoreCase = true) }

    fun getEquipmentByShortName(shortName: String): Equipment? =
        equipment.find { it.shortName.equals(shortName, ignoreCase = true) }

    fun getCounterBuild(enemyHeroes: List<String>): Map<EquipmentCategory, List<Equipment>> {
        val counters = mutableMapOf<EquipmentCategory, MutableList<Equipment>>()

        enemyHeroes.forEach { heroName ->
            equipment.filter { heroName in it.counterFor }.forEach { item ->
                counters.getOrPut(item.category) { mutableListOf() }.add(item)
            }
        }

        return counters.mapValues { it.value.distinct() }
    }

    fun getSituationalItems(): List<Equipment> = equipment.filter { it.situational }

    fun getRecommendedBuild(heroName: String, buildType: String = "balanced"): List<Equipment> {
        val hero = HeroDatabase.getHeroByName(heroName) ?: return emptyList()
        val shortNames = hero.builds[buildType] ?: hero.builds["balanced"] ?: emptyList()
        return shortNames.mapNotNull { name -> getEquipmentByShortName(name) ?: getEquipmentByName(name) }
    }

    fun getAntiHealItems(): List<Equipment> =
        equipment.filter { it.name == "Necklace of Durance" }

    fun getAntiPhysicalItems(): List<Equipment> =
        equipment.filter { it.category == EquipmentCategory.DEFENSE && it.stats.any { stat -> stat.key.contains("Physical") } }

    fun getAntiMagicItems(): List<Equipment> =
        equipment.filter { it.category == EquipmentCategory.DEFENSE && it.stats.any { stat -> stat.key.contains("Magic") } }
}