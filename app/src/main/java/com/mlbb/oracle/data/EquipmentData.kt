package com.mlbb.oracle.data

data class EquipmentStats(
    val attack: Int = 0,
    val magicPower: Int = 0,
    val hp: Int = 0,
    val mp: Int = 0,
    val defense: Int = 0,
    val magicDefense: Int = 0,
    val attackSpeed: Float = 0f,
    val movementSpeed: Float = 0f,
    val cooldownReduction: Float = 0f,
    val lifesteal: Float = 0f,
    val magicLifesteal: Float = 0f,
    val criticalChance: Float = 0f,
    val penetration: Int = 0
)

data class EquipmentData(
    val id: String,
    val name: String,
    val category: String,
    val subcategory: String,
    val price: Int,
    val stats: EquipmentStats,
    val passive: String,
    val buildFrom: List<String>,
    val tier: Int
)

object EquipmentDataStore {

    val items = listOf(
        // ═══════════════════════════════════════════
        // ATTACK ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "blade_of_despair", name = "Blade of Despair", category = "Attack", subcategory = "Physical",
            price = 3100, stats = EquipmentStats(attack = 160, criticalChance = 0.25f),
            passive = "Dauntless — Extra 25% damage when target is below 50% HP",
            buildFrom = listOf("Legion Sword", "Legion Sword"), tier = 3
        ),
        EquipmentData(
            id = "endless_battle", name = "Endless Battle", category = "Attack", subcategory = "Physical",
            price = 2550, stats = EquipmentStats(attack = 65, hp = 500, mp = 500, cooldownReduction = 0.1f, lifesteal = 0.1f),
            passive = "Divine Justice — Enhanced basic attack after skill dealing true damage",
            buildFrom = listOf("Vampire Mallet", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "blade_heptaseas", name = "Blade of the Heptaseas", category = "Attack", subcategory = "Physical",
            price = 2400, stats = EquipmentStats(attack = 70, hp = 250),
            passive = "Ambush — Enhanced basic attack on enemy hero if unseen for 5s",
            buildFrom = listOf("Legion Sword", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "malefic_roar", name = "Malefic Roar", category = "Attack", subcategory = "Physical",
            price = 2180, stats = EquipmentStats(attack = 60),
            passive = "Malefic Terror — Physical penetration increases with enemy armor",
            buildFrom = listOf("Legion Sword", "Magic Blade"), tier = 3
        ),
        EquipmentData(
            id = "hunter_strike", name = "Hunter Strike", category = "Attack", subcategory = "Physical",
            price = 2100, stats = EquipmentStats(attack = 55, cooldownReduction = 0.1f),
            passive = "Hunt — Enhanced 4th basic attack deals 3 hits + movement speed boost",
            buildFrom = listOf("Legion Sword", "Magic Blade"), tier = 3
        ),
        EquipmentData(
            id = "berserkers_fury", name = "Berserker's Fury", category = "Attack", subcategory = "Critical",
            price = 2350, stats = EquipmentStats(attack = 65, criticalChance = 0.25f),
            passive = "Doom — Critical damage increased by 40%",
            buildFrom = listOf("Legion Sword", "Crit Cloak"), tier = 3
        ),
        EquipmentData(
            id = "demon_hunter_sword", name = "Demon Hunter Sword", category = "Attack", subcategory = "Physical",
            price = 2180, stats = EquipmentStats(attack = 35, attackSpeed = 0.25f),
            passive = "Devour — Basic attacks deal 9% of target's current HP as physical damage",
            buildFrom = listOf("Dagger", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "golden_staff", name = "Golden Staff", category = "Attack", subcategory = "Attack Speed",
            price = 2100, stats = EquipmentStats(attack = 35, attackSpeed = 0.3f),
            passive = "Swift — Convert critical chance to attack speed",
            buildFrom = listOf("Dagger", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "corrosion_scythe", name = "Corrosion Scythe", category = "Attack", subcategory = "Attack Speed",
            price = 2050, stats = EquipmentStats(attack = 35, attackSpeed = 0.25f),
            passive = "Corrosion — Basic attacks slow target and increase attack speed",
            buildFrom = listOf("Dagger", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "windtalker", name = "Windtalker", category = "Attack", subcategory = "Attack Speed",
            price = 2000, stats = EquipmentStats(attack = 40, attackSpeed = 0.2f, movementSpeed = 0.1f, criticalChance = 0.1f),
            passive = "Typhoon — Basic attacks deal extra AoE magic damage",
            buildFrom = listOf("Dagger", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "rose_gold_meteor", name = "Rose Gold Meteor", category = "Attack", subcategory = "Physical",
            price = 2100, stats = EquipmentStats(attack = 60, magicDefense = 30),
            passive = "Rose Meteor — Gains shield when taking magic damage below 30% HP",
            buildFrom = listOf("Magic Blade", "Legion Sword"), tier = 3
        ),
        EquipmentData(
            id = "bloodlust_axe", name = "Bloodlust Axe", category = "Attack", subcategory = "Physical",
            price = 2180, stats = EquipmentStats(attack = 70, cooldownReduction = 0.1f),
            passive = "Bloodlust — 20% spell vamp on skills",
            buildFrom = listOf("Vampire Mallet", "Legion Sword"), tier = 3
        ),
        EquipmentData(
            id = "war_axe", name = "War Axe", category = "Attack", subcategory = "Physical",
            price = 2100, stats = EquipmentStats(attack = 35, hp = 500),
            passive = "Rampage — Stacking attack and penetration during combat",
            buildFrom = listOf("Vampire Mallet", "Legion Sword"), tier = 3
        ),
        EquipmentData(
            id = "halberd", name = "Halberd", category = "Attack", subcategory = "Anti-Heal",
            price = 2100, stats = EquipmentStats(attack = 80),
            passive = "Dominance — Reduces enemy healing by 50% for 3s",
            buildFrom = listOf("Legion Sword", "Magic Blade"), tier = 3
        ),
        EquipmentData(
            id = "necklace_of_durance", name = "Necklace of Durance", category = "Attack", subcategory = "Anti-Heal",
            price = 2100, stats = EquipmentStats(magicPower = 70, cooldownReduction = 0.1f),
            passive = "Life Drain — Reduces enemy healing by 50% for 3s",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),

        // ═══════════════════════════════════════════
        // MAGIC ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "lightning_truncheon", name = "Lightning Truncheon", category = "Magic", subcategory = "Burst",
            price = 2830, stats = EquipmentStats(magicPower = 75, mp = 500),
            passive = "Resonate — Next skill deals extra magic damage based on max MP",
            buildFrom = listOf("Magic Wand", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "holy_crystal", name = "Holy Crystal", category = "Magic", subcategory = "Burst",
            price = 2180, stats = EquipmentStats(magicPower = 100),
            passive = "Unsurpassable — Increases magic power by 25%",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),
        EquipmentData(
            id = "divine_glaive", name = "Divine Glaive", category = "Magic", subcategory = "Penetration",
            price = 2200, stats = EquipmentStats(magicPower = 70),
            passive = "Glaive — Magic penetration increases with target's magic defense",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),
        EquipmentData(
            id = "concentrated_energy", name = "Concentrated Energy", category = "Magic", subcategory = "Sustain",
            price = 2250, stats = EquipmentStats(magicPower = 70, hp = 700),
            passive = "Chaos — Skills deal magic lifesteal",
            buildFrom = listOf("Magic Wand", "Vampire Mallet"), tier = 3
        ),
        EquipmentData(
            id = "winter_truncheon", name = "Winter Truncheon", category = "Magic", subcategory = "Utility",
            price = 2120, stats = EquipmentStats(magicPower = 60, defense = 60, hp = 500),
            passive = "Frozen — Activate to become immune for 2s",
            buildFrom = listOf("Magic Wand", "Magic Blade"), tier = 3
        ),
        EquipmentData(
            id = "enchanted_talisman", name = "Enchanted Talisman", category = "Magic", subcategory = "Utility",
            price = 2250, stats = EquipmentStats(magicPower = 55, mp = 500, cooldownReduction = 0.25f),
            passive = "Mana Spring — Regenerates MP over time",
            buildFrom = listOf("Magic Wand", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "fleeting_time", name = "Fleeting Time", category = "Magic", subcategory = "Utility",
            price = 2100, stats = EquipmentStats(magicPower = 60, cooldownReduction = 0.1f),
            passive = "Divination — Kill/assist resets ultimate cooldown by 30%",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),
        EquipmentData(
            id = "calamity_reaper", name = "Calamity Reaper", category = "Magic", subcategory = "True Damage",
            price = 2250, stats = EquipmentStats(magicPower = 70, cooldownReduction = 0.1f),
            passive = "Calamity — Next basic attack after skill deals true damage",
            buildFrom = listOf("Magic Wand", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "glowing_wand", name = "Glowing Wand", category = "Magic", subcategory = "Burn",
            price = 2200, stats = EquipmentStats(magicPower = 70),
            passive = "Scorch — Skills burn enemies for 4s",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),
        EquipmentData(
            id = "necklace_durance", name = "Necklace of Durance", category = "Magic", subcategory = "Anti-Heal",
            price = 2100, stats = EquipmentStats(magicPower = 70, cooldownReduction = 0.1f),
            passive = "Life Drain — Reduces enemy healing by 50%",
            buildFrom = listOf("Magic Wand", "Magic Wand"), tier = 3
        ),

        // ═══════════════════════════════════════════
        // DEFENSE ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "dominance_ice", name = "Dominance Ice", category = "Defense", subcategory = "Physical Defense",
            price = 2050, stats = EquipmentStats(defense = 70, mp = 500, attackSpeed = -0.2f),
            passive = "Frozen — Reduces nearby enemy attack speed and movement speed",
            buildFrom = listOf("Steel Legplates", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "athenas_shield", name = "Athena's Shield", category = "Defense", subcategory = "Magic Defense",
            price = 2100, stats = EquipmentStats(magicDefense = 62, hp = 900),
            passive = "Shield — Absorbs magic damage when taking burst",
            buildFrom = listOf("Magic Necklace", "Steel Legplates"), tier = 3
        ),
        EquipmentData(
            id = "radiant_armor", name = "Radiant Armor", category = "Defense", subcategory = "Magic Defense",
            price = 1900, stats = EquipmentStats(magicDefense = 52, hp = 700),
            passive = "Radiance — Reduces continuous magic damage",
            buildFrom = listOf("Magic Necklace", "Steel Legplates"), tier = 3
        ),
        EquipmentData(
            id = "antique_cuirass", name = "Antique Cuirass", category = "Defense", subcategory = "Physical Defense",
            price = 2050, stats = EquipmentStats(defense = 70, hp = 1000),
            passive = "Dreadnought — Reduces nearby enemy physical attack",
            buildFrom = listOf("Steel Legplates", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "blade_armor", name = "Blade Armor", category = "Defense", subcategory = "Physical Defense",
            price = 2000, stats = EquipmentStats(defense = 80),
            passive = "Thorn — Reflects 25% of physical damage taken",
            buildFrom = listOf("Steel Legplates", "Dagger"), tier = 3
        ),
        EquipmentData(
            id = "queens_wings", name = "Queen's Wings", category = "Defense", subcategory = "Sustain",
            price = 2100, stats = EquipmentStats(attack = 30, hp = 1000, cooldownReduction = 0.1f),
            passive = "Demonize — Reduces damage taken below 30% HP + lifesteal",
            buildFrom = listOf("Vampire Mallet", "Steel Legplates"), tier = 3
        ),
        EquipmentData(
            id = "immortality", name = "Immortality", category = "Defense", subcategory = "Revive",
            price = 2120, stats = EquipmentStats(defense = 42, hp = 800),
            passive = "Immortal — Revive after death with 15% HP",
            buildFrom = listOf("Steel Legplates", "Magic Blade"), tier = 3
        ),
        EquipmentData(
            id = "twilight_armor", name = "Twilight Armor", category = "Defense", subcategory = "Physical Defense",
            price = 2050, stats = EquipmentStats(defense = 70, hp = 500, cooldownReduction = 0.1f),
            passive = "Twilight — Reduces burst damage",
            buildFrom = listOf("Steel Legplates", "Azure Blade"), tier = 3
        ),
        EquipmentData(
            id = "winter_crown", name = "Winter Crown", category = "Defense", subcategory = "Magic Defense",
            price = 1950, stats = EquipmentStats(magicDefense = 55, hp = 800),
            passive = "Frost — Slows nearby enemies",
            buildFrom = listOf("Magic Necklace", "Steel Legplates"), tier = 3
        ),
        EquipmentData(
            id = "thunder_belt", name = "Thunder Belt", category = "Defense", subcategory = "Physical Defense",
            price = 2100, stats = EquipmentStats(defense = 60, hp = 800, cooldownReduction = 0.1f),
            passive = "Thunder — Enhanced basic attack deals AoE damage",
            buildFrom = listOf("Steel Legplates", "Azure Blade"), tier = 3
        ),

        // ═══════════════════════════════════════════
        // MOVEMENT ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "magic_shoes", name = "Magic Shoes", category = "Movement", subcategory = "CDR",
            price = 710, stats = EquipmentStats(cooldownReduction = 0.1f, movementSpeed = 0.4f),
            passive = " —", buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "warrior_boots", name = "Warrior Boots", category = "Movement", subcategory = "Physical Defense",
            price = 750, stats = EquipmentStats(defense = 22, movementSpeed = 0.4f),
            passive = " —", buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "tough_boots", name = "Tough Boots", category = "Movement", subcategory = "Magic Defense",
            price = 750, stats = EquipmentStats(magicDefense = 22, movementSpeed = 0.4f),
            passive = " —", buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "swift_boots", name = "Swift Boots", category = "Movement", subcategory = "Attack Speed",
            price = 710, stats = EquipmentStats(attackSpeed = 0.2f, movementSpeed = 0.4f),
            passive = " —", buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "arcane_boots", name = "Arcane Boots", category = "Movement", subcategory = "Magic Penetration",
            price = 710, stats = EquipmentStats(penetration = 10, movementSpeed = 0.4f),
            passive = " —", buildFrom = listOf(), tier = 2
        ),

        // ═══════════════════════════════════════════
        // ROAM ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "conceal_roam", name = "Conceal Roam", category = "Roam", subcategory = "Roaming",
            price = 750, stats = EquipmentStats(movementSpeed = 0.4f),
            passive = "Conceal — Invis allies nearby, gain movement speed",
            buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "encourage_roam", name = "Encourage Roam", category = "Roam", subcategory = "Roaming",
            price = 750, stats = EquipmentStats(movementSpeed = 0.4f),
            passive = "Encourage — Buff nearby allies' attack and magic power",
            buildFrom = listOf(), tier = 2
        ),
        EquipmentData(
            id = "dire_hit_roam", name = "Dire Hit Roam", category = "Roam", subcategory = "Roaming",
            price = 750, stats = EquipmentStats(movementSpeed = 0.4f),
            passive = "Dire Hit — Execute enemies below 15% HP",
            buildFrom = listOf(), tier = 2
        ),

        // ═══════════════════════════════════════════
        // COMPONENT ITEMS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "legion_sword", name = "Legion Sword", category = "Component", subcategory = "Physical",
            price = 950, stats = EquipmentStats(attack = 35),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "vampire_mallet", name = "Vampire Mallet", category = "Component", subcategory = "Physical",
            price = 800, stats = EquipmentStats(attack = 20, hp = 300, lifesteal = 0.1f),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "azure_blade", name = "Azure Blade", category = "Component", subcategory = "Physical",
            price = 680, stats = EquipmentStats(attack = 25, cooldownReduction = 0.1f),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "magic_wand", name = "Magic Wand", category = "Component", subcategory = "Magic",
            price = 500, stats = EquipmentStats(magicPower = 30),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "steel_legplates", name = "Steel Legplates", category = "Component", subcategory = "Defense",
            price = 500, stats = EquipmentStats(defense = 28),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "magic_necklace", name = "Magic Necklace", category = "Component", subcategory = "Magic Defense",
            price = 500, stats = EquipmentStats(magicDefense = 28),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "magic_blade", name = "Magic Blade", category = "Component", subcategory = "Hybrid",
            price = 600, stats = EquipmentStats(attack = 15, defense = 15),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "dagger", name = "Dagger", category = "Component", subcategory = "Attack Speed",
            price = 250, stats = EquipmentStats(attack = 15),
            passive = " —", buildFrom = listOf(), tier = 1
        ),
        EquipmentData(
            id = "crit_cloak", name = "Crit Cloak", category = "Component", subcategory = "Critical",
            price = 500, stats = EquipmentStats(criticalChance = 0.12f),
            passive = " —", buildFrom = listOf(), tier = 1
        ),

        // ═══════════════════════════════════════════
        // TRINKETS
        // ═══════════════════════════════════════════
        EquipmentData(
            id = "flame_retribution", name = "Flame Retribution", category = "Trinket", subcategory = "Retribution",
            price = 0, stats = EquipmentStats(),
            passive = "Flame Retribution — Deals true damage to creeps, steals stats",
            buildFrom = listOf(), tier = 0
        ),
        EquipmentData(
            id = "flicker", name = "Flicker", category = "Trinket", subcategory = "Flicker",
            price = 0, stats = EquipmentStats(),
            passive = "Flicker — Teleport a short distance",
            buildFrom = listOf(), tier = 0
        ),
        EquipmentData(
            id = "petrify", name = "Petrify", category = "Trinket", subcategory = "Petrify",
            price = 0, stats = EquipmentStats(),
            passive = "Petrify — Stun nearby enemies",
            buildFrom = listOf(), tier = 0
        ),
        EquipmentData(
            id = "retribution", name = "Retribution", category = "Trinket", subcategory = "Retribution",
            price = 0, stats = EquipmentStats(),
            passive = "Retribution — Deal true damage to creeps",
            buildFrom = listOf(), tier = 0
        )
    )

    fun getItemsByCategory(category: String): List<EquipmentData> =
        items.filter { it.category == category }

    fun getItemsBySubcategory(subcategory: String): List<EquipmentData> =
        items.filter { it.subcategory == subcategory }

    fun getItemById(id: String): EquipmentData? =
        items.find { it.id == id }

    fun getBuildPath(item: EquipmentData): List<EquipmentData> =
        item.buildFrom.mapNotNull { getItemById(it) }

    fun getCounterBuild(enemyHeroes: List<String>): List<String> {
        val counterItems = mutableListOf<String>()

        val hasHealer = enemyHeroes.any { hero ->
            when (hero.lowercase()) {
                "angela", "estes", "rafaela", "floryn", "carmilla", "mathilda" -> true
                else -> false
            }
        }

        val hasMagicDamage = enemyHeroes.any { hero ->
            when (hero.lowercase()) {
                "kagura", "eudora", "lunox", "pharsa", "lylia", "valentina" -> true
                else -> false
            }
        }

        val hasPhysicalDamage = enemyHeroes.any { hero ->
            when (hero.lowercase()) {
                "granger", "miya", "clanede", "wanwan", "brody", "moskov" -> true
                else -> false
            }
        }

        val hasAssassin = enemyHeroes.any { hero ->
            when (hero.lowercase()) {
                "fanny", "gusion", "ling", "lancelot", "saber", "hayabusa" -> true
                else -> false
            }
        }

        val hasTank = enemyHeroes.any { hero ->
            when (hero.lowercase()) {
                "tigreal", "atlas", "khufra", "franco", "johnson", "grock" -> true
                else -> false
            }
        }

        if (hasHealer) {
            counterItems.add("Halberd / Necklace of Durance (Anti-Heal)")
        }

        if (hasMagicDamage) {
            counterItems.add("Athena's Shield / Radiant Armor (Magic Defense)")
        }

        if (hasPhysicalDamage) {
            counterItems.add("Dominance Ice / Blade Armor (Physical Defense)")
        }

        if (hasAssassin) {
            counterItems.add("Immortality (Revive) / Winter Truncheon (Invincibility)")
        }

        if (hasTank) {
            counterItems.add("Malefic Roar / Divine Glaive (Penetration)")
        }

        return counterItems
    }

    fun getRecommendedBuild(heroId: String): List<EquipmentData> {
        return when (heroId.lowercase()) {
            "granger" -> listOf("magic_shoes", "blade_of_despair", "endless_battle", "blade_heptaseas", "malefic_roar", "immortality").mapNotNull { getItemById(it) }
            "fanny" -> listOf("magic_shoes", "blade_of_despair", "endless_battle", "blade_heptaseas", "malefic_roar", "immortality").mapNotNull { getItemById(it) }
            "chou" -> listOf("tough_boots", "blade_of_despair", "endless_battle", "dominance_ice", "blade_heptaseas", "immortality").mapNotNull { getItemById(it) }
            "kagura" -> listOf("arcane_boots", "lightning_truncheon", "concentrated_energy", "holy_crystal", "divine_glaive", "winter_truncheon").mapNotNull { getItemById(it) }
            "miya" -> listOf("swift_boots", "windtalker", "berserkers_fury", "demon_hunter_sword", "malefic_roar", "immortality").mapNotNull { getItemById(it) }
            "tigreal" -> listOf("tough_boots", "dominance_ice", "athenas_shield", "antique_cuirass", "immortality", "blade_armor").mapNotNull { getItemById(it) }
            "angela" -> listOf("tough_boots", "conceal_roam", "fleeting_time", "athenas_shield", "immortality", "dominance_ice").mapNotNull { getItemById(it) }
            "atlas" -> listOf("tough_boots", "dominance_ice", "athenas_shield", "antique_cuirass", "immortality", "blade_armor").mapNotNull { getItemById(it) }
            else -> listOf("tough_boots", "dominance_ice", "athenas_shield", "antique_cuirass", "immortality", "blade_armor").mapNotNull { getItemById(it) }
        }
    }
}
