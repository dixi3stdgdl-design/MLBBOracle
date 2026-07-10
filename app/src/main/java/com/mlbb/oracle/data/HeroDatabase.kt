package com.mlbb.oracle.data

data class HeroData(
    val name: String,
    val role: HeroRole,
    val specialty: String,
    val tier: String,
    val emoji: String,
    val counters: List<String>,
    val synergies: List<String>,
    val banPriority: Int,
    val winConditions: List<String>,
    val powerSpike: PowerSpike,
    val builds: Map<String, List<String>>,
    val difficulty: Int,
    val releaseDate: String
)

enum class HeroRole(val displayName: String, val colorHex: String) {
    ASSASSIN("Assassin", "#FF4444"),
    FIGHTER("Fighter", "#FF8C00"),
    MAGE("Mage", "#9B59B6"),
    MARKSMAN("Marksman", "#3498DB"),
    TANK("Tank", "#27AE60"),
    SUPPORT("Support", "#E91E63")
}

data class PowerSpike(
    val early: Int,   // 1-10
    val mid: Int,     // 1-10
    val late: Int     // 1-10
)

object HeroDatabase {
    private val heroes = listOf(
        // ==================== ASSASSINS ====================
        HeroData(
            name = "Fanny", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "S", emoji = "🦅",
            counters = listOf("Chou", "Khufra", "Atlas", "Minsitthar", "Natalia"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Mathilda"),
            banPriority = 95,
            winConditions = listOf("Cut enemy jungle early", "Secure Lord with Retri", "One-shot squishy targets"),
            powerSpike = PowerSpike(8, 9, 6),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Blade of Heptaseas"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Antique Cuirass", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 10, releaseDate = "2017-03"
        ),
        HeroData(
            name = "Gusion", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "S", emoji = "🗡️",
            counters = listOf("Chou", "Lancelot", "Natalia", "Kaja", "Minsitthar"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Faramis", "Mathilda"),
            banPriority = 92,
            winConditions = listOf("Reset combos on squishies", "Secure objectives with burst", "Snowball early kills"),
            powerSpike = PowerSpike(7, 9, 7),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Queen's Wings", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 9, releaseDate = "2017-08"
        ),
        HeroData(
            name = "Lancelot", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "A", emoji = "⚔️",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Diggie", "Mathilda", "Faramis"),
            banPriority = 78,
            winConditions = listOf("Dash resets for mobility", "Pick off isolated targets", "Snowball with early kills"),
            powerSpike = PowerSpike(7, 8, 6),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Antique Cuirass", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 9, releaseDate = "2017-06"
        ),
        HeroData(
            name = "Hayabusa", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "A", emoji = "🥷",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Diggie", "Estes", "Mathilda"),
            banPriority = 72,
            winConditions = listOf("Shadow clone ganks", "Backline assassination", "Split push pressure"),
            powerSpike = PowerSpike(6, 8, 7),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Queen's Wings", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 8, releaseDate = "2016-11"
        ),
        HeroData(
            name = "Benedetta", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "S", emoji = "🗡️",
            counters = listOf("Chou", "Khufra", "Natalia", "Kaja", "Minsitthar"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Mathilda"),
            banPriority = 88,
            winConditions = listOf("Dash resets for mobility", "Zone control with ult", "Backline dive"),
            powerSpike = PowerSpike(7, 9, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Antique Cuirass", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 9, releaseDate = "2020-04"
        ),
        HeroData(
            name = "Ling", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "S", emoji = "🗡️",
            counters = listOf("Chou", "Khufra", "Atlas", "Natalia", "Minsitthar"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Mathilda"),
            banPriority = 90,
            winConditions = listOf("Wall jumping for mobility", "Backline assassination", "Split push with ult"),
            powerSpike = PowerSpike(6, 9, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Queen's Wings", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 10, releaseDate = "2019-11"
        ),
        HeroData(
            name = "Saber", role = HeroRole.ASSASSIN, specialty = "Burst/Jungle",
            tier = "B", emoji = "⚔️",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Diggie", "Mathilda", "Faramis"),
            banPriority = 45,
            winConditions = listOf("Single target elimination", "Bush camping", "Gank snowball"),
            powerSpike = PowerSpike(7, 7, 4),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Antique Cuirass", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Natalia", role = HeroRole.ASSASSIN, specialty = "Roam/Assassinate",
            tier = "A", emoji = "🐱",
            counters = listOf("Chou", "Kaja", "Minsitthar", "Diggie", "Angela"),
            synergies = listOf("Lancelot", "Fanny", "Gusion", "Hayabusa", "Benedetta"),
            banPriority = 75,
            winConditions = listOf("Silence enemy jungler", "Vision control via invisibility", "Pick off isolated targets"),
            powerSpike = PowerSpike(8, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Endless Battle", "Blade of Heptaseas", "Hunter Strike", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Hunter Strike", "Tough Boots"),
                "defensive" to listOf("Blade of Despair", "Immortality", "Athena's Shield", "Antique Cuirass", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 8, releaseDate = "2016-12"
        ),

        // ==================== FIGHTERS ====================
        HeroData(
            name = "Chou", role = HeroRole.FIGHTER, specialty = "Control/Fighter",
            tier = "S", emoji = "👊",
            counters = listOf("Esmeralda", "Yu Zhong", "Thamuz", "Paquito", "X.Borg"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Diggie", "Mathilda"),
            banPriority = 94,
            winConditions = listOf("Kick enemy carry into team", "Zone control", "Split push pressure"),
            powerSpike = PowerSpike(7, 8, 7),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Endless Battle", "Bloodlust Axe", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 8, releaseDate = "2017-03"
        ),
        HeroData(
            name = "Yu Zhong", role = HeroRole.FIGHTER, specialty = "Sustain/Fighter",
            tier = "S", emoji = "🐉",
            counters = listOf("Esmeralda", "Thamuz", "X.Borg", "Karrie", "Claude"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Faramis", "Floryn"),
            banPriority = 91,
            winConditions = listOf("Dragon form engage", "Sustain through fights", "Zone with ult"),
            powerSpike = PowerSpike(6, 8, 8),
            builds = mapOf(
                "aggressive" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Blade of Despair", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Queen's Wings", "Immortality", "Antique Cuirass", "Athena's Shield", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2019-08"
        ),
        HeroData(
            name = "Esmeralda", role = HeroRole.FIGHTER, specialty = "Sustain/Mage",
            tier = "A", emoji = "💫",
            counters = listOf("Karrie", "Claude", "Lesley", "Thamuz", "X.Borg"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Faramis", "Floryn"),
            banPriority = 82,
            winConditions = listOf("Shield stealing", "Sustain fights", "Zone control"),
            powerSpike = PowerSpike(5, 8, 9),
            builds = mapOf(
                "aggressive" to listOf("Concentrated Energy", "Necklace of Durance", "Holy Crystal", "Divine Glaive", "Immortality", "Magic Shoes"),
                "balanced" to listOf("Concentrated Energy", "Immortality", "Athena's Shield", "Antique Cuirass", "Holy Crystal", "Magic Shoes"),
                "defensive" to listOf("Concentrated Energy", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2019-03"
        ),
        HeroData(
            name = "Paquito", role = HeroRole.FIGHTER, specialty = "Burst/Jungle",
            tier = "A", emoji = "🥊",
            counters = listOf("Esmeralda", "Yu Zhong", "Thamuz", "X.Borg", "Karrie"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Diggie", "Mathilda"),
            banPriority = 80,
            winConditions = listOf("Reset combos for burst", "Zone control with CC", "Snowball early kills"),
            powerSpike = PowerSpike(7, 8, 6),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 8, releaseDate = "2020-11"
        ),
        HeroData(
            name = "Thamuz", role = HeroRole.FIGHTER, specialty = "Sustain/Jungle",
            tier = "A", emoji = "🔥",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "X.Borg"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Faramis", "Floryn"),
            banPriority = 70,
            winConditions = listOf("Sustain through fights", "Zone control with fire", "Split push pressure"),
            powerSpike = PowerSpike(6, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Bloodlust Axe", "Blade of Despair", "Endless Battle", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2018-08"
        ),
        HeroData(
            name = "X.Borg", role = HeroRole.FIGHTER, specialty = "Sustain/Jungle",
            tier = "A", emoji = "🔥",
            counters = listOf("Karrie", "Claude", "Lesley", "Esmeralda", "Thamuz"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Faramis", "Floryn"),
            banPriority = 68,
            winConditions = listOf("Armor break and reset", "Zone with fire", "Sustain through fights"),
            powerSpike = PowerSpike(5, 7, 8),
            builds = mapOf(
                "aggressive" to listOf("Bloodlust Axe", "Blade of Despair", "Endless Battle", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2019-05"
        ),
        HeroData(
            name = "Aldous", role = HeroRole.FIGHTER, specialty = "Burst/Split",
            tier = "B", emoji = "👊",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Diggie", "Mathilda", "Faramis"),
            banPriority = 50,
            winConditions = listOf("Stack farming", "One-shot with ult", "Split push pressure"),
            powerSpike = PowerSpike(4, 6, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Endless Battle", "Bloodlust Axe", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2017-11"
        ),
        HeroData(
            name = "Badang", role = HeroRole.FIGHTER, specialty = "Burst/Fighter",
            tier = "B", emoji = "👊",
            counters = listOf("Chou", "Esmeralda", "Yu Zhong", "Thamuz", "X.Borg"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Mathilda"),
            banPriority = 40,
            winConditions = listOf("Wall combo burst", "Zone control", "Split push"),
            powerSpike = PowerSpike(6, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Warrior Boots"),
                "balanced" to listOf("Bloodlust Axe", "Endless Battle", "Queen's Wings", "Immortality", "Antique Cuirass", "Warrior Boots"),
                "defensive" to listOf("Bloodlust Axe", "Immortality", "Antique Cuirass", "Athena's Shield", "Queen's Wings", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2018-01"
        ),

        // ==================== MAGES ====================
        HeroData(
            name = "Kagura", role = HeroRole.MAGE, specialty = "Burst/Mage",
            tier = "S", emoji = "☂️",
            counters = listOf("Chou", "Khufra", "Natalia", "Kaja", "Minsitthar"),
            synergies = listOf("Angela", "Estes", "Rafaela", "Diggie", "Mathilda"),
            banPriority = 88,
            winConditions = listOf("Umbrella poke and burst", "Zoning with ult", "Team fight control"),
            powerSpike = PowerSpike(5, 8, 8),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Concentrated Energy", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 8, releaseDate = "2016-12"
        ),
        HeroData(
            name = "Lunox", role = HeroRole.MAGE, specialty = "Burst/Mage",
            tier = "S", emoji = "✨",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Mathilda"),
            banPriority = 85,
            winConditions = listOf("Chaos assault burst", "Order ult sustain", "Team fight control"),
            powerSpike = PowerSpike(5, 8, 8),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Concentrated Energy", "Lightning Truncheon", "Glowing Wand", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 9, releaseDate = "2018-04"
        ),
        HeroData(
            name = "Pharsa", role = HeroRole.MAGE, specialty = "Long-range/Mage",
            tier = "A", emoji = "🦅",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 75,
            winConditions = listOf("Long-range poke", "Ult zoning", "Safe backline damage"),
            powerSpike = PowerSpike(5, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2017-09"
        ),
        HeroData(
            name = "Valir", role = HeroRole.MAGE, specialty = "Control/Mage",
            tier = "A", emoji = "🔥",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 72,
            winConditions = listOf("CC chains", "Zone control", "Anti-dive with pushback"),
            powerSpike = PowerSpike(6, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2019-01"
        ),
        HeroData(
            name = "Chang'e", role = HeroRole.MAGE, specialty = "Poke/Mage",
            tier = "A", emoji = "🌙",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 65,
            winConditions = listOf("Long-range poke", "Objective control with ult", "Safe backline damage"),
            powerSpike = PowerSpike(5, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2017-11"
        ),
        HeroData(
            name = "Harley", role = HeroRole.MAGE, specialty = "Burst/Mage",
            tier = "A", emoji = "🎩",
            counters = listOf("Chou", "Natalia", "Kaja", "Minsitthar", "Khufra"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Mathilda"),
            banPriority = 70,
            winConditions = listOf("Card trick burst", "Pick off with ult", "Snowball early kills"),
            powerSpike = PowerSpike(7, 8, 6),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Concentrated Energy", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2017-05"
        ),
        HeroData(
            name = "Eudora", role = HeroRole.MAGE, specialty = "Burst/Mage",
            tier = "B", emoji = "⚡",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 35,
            winConditions = listOf("Single target burst", "Bush camping", "CC chain with team"),
            powerSpike = PowerSpike(7, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 2, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Aurora", role = HeroRole.MAGE, specialty = "Control/Mage",
            tier = "B", emoji = "❄️",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 30,
            winConditions = listOf("CC combo with freeze", "Team fight control", "Bush camping"),
            powerSpike = PowerSpike(6, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-08"
        ),
        HeroData(
            name = "Vexana", role = HeroRole.MAGE, specialty = "Control/Mage",
            tier = "B", emoji = "👻",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 28,
            winConditions = listOf("Puppet control", "Zone with ult", "CC chain with team"),
            powerSpike = PowerSpike(5, 6, 6),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Cyclops", role = HeroRole.MAGE, specialty = "Poke/Mage",
            tier = "B", emoji = "🔮",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 25,
            winConditions = listOf("Poke and chase", "Objective control", "Safe backline damage"),
            powerSpike = PowerSpike(5, 6, 6),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Concentrated Energy", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Gord", role = HeroRole.MAGE, specialty = "Poke/Mage",
            tier = "B", emoji = "🏄",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 20,
            winConditions = listOf("Long-range poke", "Zone with ult", "Safe backline damage"),
            powerSpike = PowerSpike(5, 6, 6),
            builds = mapOf(
                "aggressive" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand", "Necklace of Durance", "Magic Shoes"),
                "balanced" to listOf("Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Immortality", "Athena's Shield", "Magic Shoes"),
                "defensive" to listOf("Holy Crystal", "Divine Glaive", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 2, releaseDate = "2016-07"
        ),

        // ==================== MARKSMEN ====================
        HeroData(
            name = "Granger", role = HeroRole.MARKSMAN, specialty = "Burst/Marksman",
            tier = "S", emoji = "🔫",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 82,
            winConditions = listOf("Burst damage with ult", "Objective control", "Safe positioning"),
            powerSpike = PowerSpike(5, 8, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2018-06"
        ),
        HeroData(
            name = "Wanwan", role = HeroRole.MARKSMAN, specialty = "Burst/Marksman",
            tier = "S", emoji = "🎯",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 80,
            winConditions = listOf("Crossbow burst", "Jump reset mobility", "Backline assassination"),
            powerSpike = PowerSpike(5, 7, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 9, releaseDate = "2019-06"
        ),
        HeroData(
            name = "Miya", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "A", emoji = "🏹",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 55,
            winConditions = listOf("Late game DPS", "Split push with ult", "Safe farming"),
            powerSpike = PowerSpike(3, 6, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Karrie", role = HeroRole.MARKSMAN, specialty = "True Damage/Marksman",
            tier = "A", emoji = "💫",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 60,
            winConditions = listOf("True damage shredding", "Safe positioning", "Objective control"),
            powerSpike = PowerSpike(4, 7, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Demon Hunter Sword", "Berserker's Fury", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Demon Hunter Sword", "Berserker's Fury", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2017-06"
        ),
        HeroData(
            name = "Claude", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "A", emoji = "🐒",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 62,
            winConditions = listOf("Dexter ult DPS", "Jump mobility", "Safe farming and split push"),
            powerSpike = PowerSpike(4, 7, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Demon Hunter Sword", "Windtalker", "Berserker's Fury", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Demon Hunter Sword", "Windtalker", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2018-02"
        ),
        HeroData(
            name = "Lesley", role = HeroRole.MARKSMAN, specialty = "Snipe/Marksman",
            tier = "B", emoji = "🎯",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 45,
            winConditions = listOf("True damage sniping", "Safe backline", "Pick off isolated targets"),
            powerSpike = PowerSpike(5, 7, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2017-10"
        ),
        HeroData(
            name = "Layla", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "B", emoji = "🔫",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 15,
            winConditions = listOf("Late game DPS", "Long-range poke", "Safe positioning"),
            powerSpike = PowerSpike(3, 5, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 1, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Bruno", role = HeroRole.MARKSMAN, specialty = "Burst/Marksman",
            tier = "B", emoji = "⚽",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 30,
            winConditions = listOf("Ball bouncing burst", "Safe farming", "Objective control"),
            powerSpike = PowerSpike(5, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Irithel", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "B", emoji = "🐅",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 20,
            winConditions = listOf("Late game DPS", "Ult mobility", "Safe farming"),
            powerSpike = PowerSpike(3, 5, 9),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Moskov", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "B", emoji = "🗡️",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 25,
            winConditions = listOf("Wall pin burst", "Late game DPS", "Safe positioning"),
            powerSpike = PowerSpike(4, 6, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Endless Battle", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2016-11"
        ),
        HeroData(
            name = "Hanabi", role = HeroRole.MARKSMAN, specialty = "DPS/Marksman",
            tier = "B", emoji = "🌸",
            counters = listOf("Fanny", "Ling", "Lancelot", "Natalia", "Benedetta"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Diggie", "Atlas"),
            banPriority = 18,
            winConditions = listOf("CC bounce", "Late game DPS", "Safe positioning"),
            powerSpike = PowerSpike(3, 5, 8),
            builds = mapOf(
                "aggressive" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Windtalker", "Malefic Roar", "Swift Boots"),
                "balanced" to listOf("Blade of Despair", "Berserker's Fury", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Swift Boots"),
                "defensive" to listOf("Blade of Despair", "Demon Hunter Sword", "Immortality", "Athena's Shield", "Antique Cuirass", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2016-07"
        ),

        // ==================== TANKS ====================
        HeroData(
            name = "Tigreal", role = HeroRole.TANK, specialty = "Control/Tank",
            tier = "A", emoji = "🛡️",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 60,
            winConditions = listOf("Initiate team fights", "CC chain with ult", "Protect backline"),
            powerSpike = PowerSpike(6, 7, 6),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Atlas", role = HeroRole.TANK, specialty = "Control/Tank",
            tier = "S", emoji = "🤖",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 85,
            winConditions = listOf("5-man ult combo", "Initiate team fights", "Zone control"),
            powerSpike = PowerSpike(6, 8, 7),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2019-09"
        ),
        HeroData(
            name = "Khufra", role = HeroRole.TANK, specialty = "Control/Tank",
            tier = "S", emoji = "🥊",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 80,
            winConditions = listOf("Anti-dive CC", "Initiate team fights", "Zone control"),
            powerSpike = PowerSpike(7, 8, 6),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 7, releaseDate = "2018-12"
        ),
        HeroData(
            name = "Johnson", role = HeroRole.TANK, specialty = "Initiate/Tank",
            tier = "A", emoji = "🚗",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Odette", "Angela", "Rafaela", "Estes", "Faramis"),
            banPriority = 65,
            winConditions = listOf("Car crash initiation", "Map roaming", "Team fight setup"),
            powerSpike = PowerSpike(7, 7, 6),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2016-11"
        ),
        HeroData(
            name = "Grock", role = HeroRole.TANK, specialty = "Initiate/Tank",
            tier = "A", emoji = "🗿",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 55,
            winConditions = listOf("Wall creation", "Anti-magic zone", "Initiate team fights"),
            powerSpike = PowerSpike(7, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2017-07"
        ),
        HeroData(
            name = "Akai", role = HeroRole.TANK, specialty = "Control/Tank",
            tier = "A", emoji = "🐼",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 50,
            winConditions = listOf("Spin CC", "Protect carry", "Zone control"),
            powerSpike = PowerSpike(6, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-08"
        ),
        HeroData(
            name = "Franco", role = HeroRole.TANK, specialty = "Initiate/Roam",
            tier = "B", emoji = "🪝",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 45,
            winConditions = listOf("Hook pick-off", "Map control", "Objective security"),
            powerSpike = PowerSpike(7, 6, 5),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Hylos", role = HeroRole.TANK, specialty = "Sustain/Tank",
            tier = "B", emoji = "🐴",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 40,
            winConditions = listOf("Sustain frontline", "Zone control", "Map roaming"),
            powerSpike = PowerSpike(6, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2017-04"
        ),
        HeroData(
            name = "Uranus", role = HeroRole.TANK, specialty = "Sustain/Tank",
            tier = "B", emoji = "🛡️",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 35,
            winConditions = listOf("Sustain frontline", "Split push", "Zone control"),
            powerSpike = PowerSpike(5, 7, 6),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2017-12"
        ),
        HeroData(
            name = "Minotaur", role = HeroRole.TANK, specialty = "Control/Tank",
            tier = "B", emoji = "🐂",
            counters = listOf("Esmeralda", "Karrie", "Claude", "Lesley", "Diggie"),
            synergies = listOf("Angela", "Rafaela", "Estes", "Faramis", "Floryn"),
            banPriority = 30,
            winConditions = listOf("Ult CC chain", "Heal frontline", "Initiate team fights"),
            powerSpike = PowerSpike(6, 7, 5),
            builds = mapOf(
                "aggressive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots"),
                "balanced" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Radiant Armor", "Tough Boots"),
                "defensive" to listOf("Dominance Ice", "Antique Cuirass", "Athena's Shield", "Immortality", "Blade Armor", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-07"
        ),

        // ==================== SUPPORTS ====================
        HeroData(
            name = "Angela", role = HeroRole.SUPPORT, specialty = "Heal/Support",
            tier = "S", emoji = "💕",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Diggie", "Khufra"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 90,
            winConditions = listOf("Ult attach to carry", "Heal sustain", "CC save allies"),
            powerSpike = PowerSpike(7, 8, 7),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 5, releaseDate = "2017-08"
        ),
        HeroData(
            name = "Estes", role = HeroRole.SUPPORT, specialty = "Heal/Support",
            tier = "A", emoji = "🌿",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Diggie", "Khufra"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 75,
            winConditions = listOf("Heal frontline sustain", "Team fight healing", "Protect carry"),
            powerSpike = PowerSpike(6, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 4, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Rafaela", role = HeroRole.SUPPORT, specialty = "Heal/Support",
            tier = "A", emoji = "👼",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Diggie", "Khufra"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 70,
            winConditions = listOf("Heal and speed boost", "CC peel for carry", "Team fight sustain"),
            powerSpike = PowerSpike(6, 7, 7),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2016-07"
        ),
        HeroData(
            name = "Diggie", role = HeroRole.SUPPORT, specialty = "Control/Support",
            tier = "A", emoji = "⏰",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Khufra", "Atlas"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 78,
            winConditions = listOf("Anti-CC egg", "Bomb CC", "Protect carry from dive"),
            powerSpike = PowerSpike(7, 7, 6),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2017-10"
        ),
        HeroData(
            name = "Mathilda", role = HeroRole.SUPPORT, specialty = "Mobility/Support",
            tier = "A", emoji = "🦋",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Diggie", "Khufra"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 72,
            winConditions = listOf("Mobility boost", "Pick off with ult", "Save allies with dash"),
            powerSpike = PowerSpike(7, 8, 6),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 6, releaseDate = "2020-08"
        ),
        HeroData(
            name = "Floryn", role = HeroRole.SUPPORT, specialty = "Heal/Support",
            tier = "B", emoji = "🌸",
            counters = listOf("Natalia", "Kaja", "Minsitthar", "Diggie", "Khufra"),
            synergies = listOf("Fanny", "Gusion", "Ling", "Lancelot", "Benedetta"),
            banPriority = 45,
            winConditions = listOf("Global heal", "CC peel", "Team fight sustain"),
            powerSpike = PowerSpike(5, 6, 7),
            builds = mapOf(
                "aggressive" to listOf("Necklace of Durance", "Holy Crystal", "Concentrated Energy", "Immortality", "Athena's Shield", "Magic Shoes"),
                "balanced" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Concentrated Energy", "Magic Shoes"),
                "defensive" to listOf("Necklace of Durance", "Immortality", "Athena's Shield", "Antique Cuirass", "Radiant Armor", "Tough Boots")
            ),
            difficulty = 3, releaseDate = "2020-01"
        )
    )

    fun getAllHeroes(): List<HeroData> = heroes

    fun getHeroByName(name: String): HeroData? = heroes.find { it.name.equals(name, ignoreCase = true) }

    fun getHeroesByRole(role: HeroRole): List<HeroData> = heroes.filter { it.role == role }

    fun getHeroesByTier(tier: String): List<HeroData> = heroes.filter { it.tier == tier }

    fun getTopBans(count: Int = 10): List<HeroData> = heroes.sortedByDescending { it.banPriority }.take(count)

    fun getCounterPicks(heroName: String): List<HeroData> {
        val hero = getHeroByName(heroName) ?: return emptyList()
        return heroes.filter { it.name in hero.counters }
    }

    fun getSynergyPicks(heroName: String): List<HeroData> {
        val hero = getHeroByName(heroName) ?: return emptyList()
        return heroes.filter { it.name in hero.synergies }
    }

    fun getRecommendedBan(teamPicks: List<String>, enemyPicks: List<String>): List<HeroData> {
        val usedHeroes = teamPicks + enemyPicks
        return heroes
            .filter { it.name !in usedHeroes }
            .sortedByDescending { it.banPriority }
            .take(5)
    }

    fun getRecommendedPick(teamPicks: List<String>, enemyPicks: List<String>, role: HeroRole? = null): List<HeroData> {
        val usedHeroes = teamPicks + enemyPicks
        return heroes
            .filter { it.name !in usedHeroes && (role == null || it.role == role) }
            .sortedByDescending { hero ->
                var score = hero.banPriority / 10.0
                enemyPicks.forEach { enemyName ->
                    if (enemyName in hero.counters) score += 20
                }
                teamPicks.forEach { allyName ->
                    if (allyName in hero.synergies) score += 15
                }
                score
            }
            .take(10)
    }

    fun analyzeTeamComposition(picks: List<String>): TeamAnalysis {
        val heroData = picks.mapNotNull { getHeroByName(it) }
        val roles = heroData.groupBy { it.role }
        val avgPowerSpike = heroData.map { it.powerSpike }
        val earlyAvg = avgPowerSpike.map { it.early }.average()
        val midAvg = avgPowerSpike.map { it.mid }.average()
        val lateAvg = avgPowerSpike.map { it.late }.average()

        val missingRoles = HeroRole.entries.filter { roles[it]?.isEmpty() == true }
        val hasTank = roles[HeroRole.TANK]?.isNotEmpty() == true
        val hasSupport = roles[HeroRole.SUPPORT]?.isNotEmpty() == true
        val hasMarksman = roles[HeroRole.MARKSMAN]?.isNotEmpty() == true
        val hasMage = roles[HeroRole.MAGE]?.isNotEmpty() == true
        val hasAssassin = roles[HeroRole.ASSASSIN]?.isNotEmpty() == true
        val hasFighter = roles[HeroRole.FIGHTER]?.isNotEmpty() == true

        val weaknesses = mutableListOf<String>()
        if (!hasTank) weaknesses.add("No tank - vulnerable to dive")
        if (!hasSupport) weaknesses.add("No support - limited sustain")
        if (!hasMarksman) weaknesses.add("No marksman - weak late game DPS")
        if (!hasMage) weaknesses.add("No mage - weak magic damage")
        if (!hasAssassin) weaknesses.add("No assassin - weak backline access")
        if (!hasFighter) weaknesses.add("No fighter - weak frontline")

        val synergyScore = calculateSynergyScore(picks)

        return TeamAnalysis(
            roles = roles.mapValues { it.value.size },
            missingRoles = missingRoles,
            earlyGamePower = earlyAvg,
            midGamePower = midAvg,
            lateGamePower = lateAvg,
            weaknesses = weaknesses,
            synergyScore = synergyScore,
            overallRating = calculateOverallRating(heroData, synergyScore, missingRoles.size)
        )
    }

    private fun calculateSynergyScore(picks: List<String>): Double {
        var score = 0.0
        picks.forEach { pick ->
            val hero = getHeroByName(pick) ?: return@forEach
            picks.forEach { other ->
                if (other != pick) {
                    if (other in hero.synergies) score += 10
                    if (pick in (getHeroByName(other)?.synergies ?: emptyList())) score += 10
                }
            }
        }
        return score.coerceIn(0.0, 100.0)
    }

    private fun calculateOverallRating(heroes: List<HeroData>, synergyScore: Double, missingRoles: Int): Double {
        val avgTier = heroes.map { when(it.tier) { "S" -> 4.0; "A" -> 3.0; "B" -> 2.0; else -> 1.0 } }.average()
        val tierScore = avgTier * 20
        val rolePenalty = missingRoles * 5.0
        return (tierScore + synergyScore - rolePenalty).coerceIn(0.0, 100.0)
    }
}

data class TeamAnalysis(
    val roles: Map<HeroRole, Int>,
    val missingRoles: List<HeroRole>,
    val earlyGamePower: Double,
    val midGamePower: Double,
    val lateGamePower: Double,
    val weaknesses: List<String>,
    val synergyScore: Double,
    val overallRating: Double
)