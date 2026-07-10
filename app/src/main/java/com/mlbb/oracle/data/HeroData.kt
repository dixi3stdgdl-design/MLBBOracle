package com.mlbb.oracle.data

import androidx.compose.ui.graphics.Color

data class HeroStats(
    val hp: Int,
    val mp: Int,
    val attack: Int,
    val defense: Int,
    val speed: Float
)

data class HeroSkills(
    val passive: String,
    val skill1: String,
    val skill2: String,
    val ultimate: String
)

data class Build(
    val name: String,
    val items: List<String>,
    val description: String
)

data class HeroData(
    val id: String,
    val name: String,
    val nameCn: String,
    val role: String,
    val specialty: String,
    val stats: HeroStats,
    val skills: HeroSkills,
    val builds: List<Build>,
    val emblem: String,
    val battleSpell: String,
    val counters: List<String>,
    val synergies: List<String>,
    val winRate: Float,
    val pickRate: Float,
    val banRate: Float,
    val tier: String
)

object HeroDatabase {

    val roleColors = mapOf(
        "Assassin" to Color(0xFFE53935),
        "Fighter" to Color(0xFFFF9800),
        "Mage" to Color(0xFF9C27B0),
        "Marksman" to Color(0xFF2196F3),
        "Tank" to Color(0xFF4CAF50),
        "Support" to Color(0xFFE91E63)
    )

    val heroes = listOf(
        // ═══════════════════════════════════════════
        // ASSASSINS
        // ═══════════════════════════════════════════
        HeroData(
            id = "fanny",
            name = "Fanny", nameCn = "梵妮",
            role = "Assassin", specialty = "Burst/Jungle",
            stats = HeroStats(2580, 340, 165, 110, 3.8f),
            skills = HeroSkills(
                passive = "Air Superiority — Deals more damage from behind",
                skill1 = "Tornado Strike — AoE spin dealing physical damage",
                skill2 = "Steel Cable — Shoots cables to walls for mobility",
                ultimate = "Cut Throat — Single target burst with execute damage"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Balanced burst + sustain"),
                Build("Damage", listOf("Tough Boots", "Blade of Despair", "Blade of the Heptaseas", "Hunter Strike", "Malefic Roar", "Rose Gold Meteor"), "Max burst"),
                Build("Snowball", listOf("Magic Shoes", "Blade of Despair", "Blade of the Heptaseas", "Endless Battle", "Windtalker", "Immortality"), "Early aggression")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Chou", "Lolita", "Kaja"),
            synergies = listOf("Kaja", "Atlas", "Estes"),
            winRate = 52.3f, pickRate = 12.8f, banRate = 45.2f, tier = "S"
        ),
        HeroData(
            id = "gusion",
            name = "Gusion", nameCn = "古辛",
            role = "Assassin", specialty = "Burst/Poke",
            stats = HeroStats(2650, 410, 158, 105, 3.6f),
            skills = HeroSkills(
                passive = "Dagger Specialist — Enhanced basic attack after skills",
                skill1 = "Sword Spike — Throws dagger, marks enemy for recall",
                skill2 = "Shadowblade Slaughter — Throws 5 daggers outward",
                ultimate = "Incandescence — Resets cooldowns, gains movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Concentrated Energy", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Immortality"), "Burst magic assassin"),
                Build("Snowball", listOf("Tough Boots", "Concentrated Energy", "Calamity Reaper", "Lightning Truncheon", "Holy Crystal", "Winter Truncheon"), "Early kill pressure"),
                Build("Sustain", listOf("Arcane Boots", "Concentrated Energy", "Calamity Reaper", "Winter Truncheon", "Holy Crystal", "Immortality"), "Survivability")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Kaja", "Chou", "Ruby"),
            synergies = listOf("Kaja", "Estes", "Angela"),
            winRate = 51.8f, pickRate = 14.2f, banRate = 38.7f, tier = "S"
        ),
        HeroData(
            id = "ling",
            name = "Ling", nameCn = "凌",
            role = "Assassin", specialty = "Mobility/Burst",
            stats = HeroStats(2520, 360, 172, 108, 4.0f),
            skills = HeroSkills(
                passive = "Cloud Walker — Gains movement speed near walls",
                skill1 = "Finch Poise — Jumps to a wall, enters stealth",
                skill2 = "Defiant Sword — Dash dealing physical damage",
                ultimate = "Tempest of Blades — Invincible leap, knocks up enemies"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Standard burst"),
                Build("Full Damage", listOf("Tough Boots", "Blade of Despair", "Blade of the Heptaseas", "Hunter Strike", "Malefic Roar", "Rose Gold Meteor"), "Glass cannon"),
                Build("Sustain", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Malefic Roar"), "Survival focused")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Saber", "Eudora", "Franco"),
            synergies = listOf("Atlas", "Kaja", "Angela"),
            winRate = 50.5f, pickRate = 11.3f, banRate = 42.1f, tier = "S"
        ),
        HeroData(
            id = "lancelot",
            name = "Lancelot", nameCn = "兰斯洛特",
            role = "Assassin", specialty = "Burst/Chase",
            stats = HeroStats(2610, 350, 168, 112, 3.7f),
            skills = HeroSkills(
                passive = "Soul Cutter — Marked enemies take 15% more damage",
                skill1 = "Puncture — Dash dealing damage, resets on hit",
                skill2 = "Thorned Rose — Triangle AoE dealing 3 hits",
                ultimate = "Phantom Execution — Long range dash with burst damage"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Standard burst build"),
                Build("Damage", listOf("Tough Boots", "Blade of Despair", "Hunter Strike", "Blade of the Heptaseas", "Malefic Roar", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Chou", "Saber", "Eudora"),
            synergies = listOf("Atlas", "Kaja", "Estes"),
            winRate = 51.2f, pickRate = 10.5f, banRate = 35.8f, tier = "A"
        ),
        HeroData(
            id = "saber",
            name = "Saber", nameCn = "剑豪",
            role = "Assassin", specialty = "Burst/Pick-off",
            stats = HeroStats(2700, 320, 170, 115, 3.6f),
            skills = HeroSkills(
                passive = "Combo Strike — Enhanced basic attack after skill",
                skill1 = "Flying Sword — Throws 4 rotating swords",
                skill2 = "Charge — Dash, leaves swords behind",
                ultimate = "Triple Sweep — Knockup + 3 hits on single target"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Standard pick-off"),
                Build("Burst", listOf("Magic Shoes", "Blade of Despair", "Hunter Strike", "Malefic Roar", "Blade of the Heptaseas", "Rose Gold Meteor"), "Max burst on squishies")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Chou", "Ruby", "Esmeralda"),
            synergies = listOf("Kaja", "Atlas", "Angela"),
            winRate = 50.8f, pickRate = 9.4f, banRate = 28.3f, tier = "A"
        ),
        HeroData(
            id = "hayabusa",
            name = "Hayabusa", nameCn = "隼",
            role = "Assassin", specialty = "Burst/Split-push",
            stats = HeroStats(2550, 330, 162, 106, 3.9f),
            skills = HeroSkills(
                passive = "Ninjutsu — Stacking mark, deals more per mark",
                skill1 = "Ninjutsu: Phantom Shuriken — Throws 3 shurikens",
                skill2 = "Ninjutsu: Quad Shadow — Creates 4 shadows",
                ultimate = "Ougi: Shadow Kill — Untargetable, hits random enemies"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Standard burst split"),
                Build("Damage", listOf("Tough Boots", "Blade of Despair", "Hunter Strike", "Malefic Roar", "Rose Gold Meteor", "Immortality"), "Full damage")
            ),
            emblem = "Custom Assassin", battleSpell = "Retribution",
            counters = listOf("Saber", "Eudora", "Franco"),
            synergies = listOf("Kaja", "Atlas", "Estes"),
            winRate = 49.8f, pickRate = 7.2f, banRate = 18.5f, tier = "B"
        ),

        // ═══════════════════════════════════════════
        // FIGHTERS
        // ═══════════════════════════════════════════
        HeroData(
            id = "chou",
            name = "Chou", nameCn = "周",
            role = "Fighter", specialty = "Chase/Burst",
            stats = HeroStats(3200, 280, 175, 165, 3.4f),
            skills = HeroSkills(
                passive = "Only Fast — Enhanced basic attack every 8s",
                skill1 = "Jeet Kune Do — 3-stage punch, knocks up on 3rd",
                skill2 = "Shunpo — Dash, immune to CC briefly",
                ultimate = "The Way of Dragon — Kicks enemy back, 2nd activation for combo"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Dominance Ice", "Blade of the Heptaseas", "Immortality"), "Standard fighter build"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "Frontline tank"),
                Build("Damage", listOf("Warrior Boots", "Blade of Despair", "Endless Battle", "Hunter Strike", "Malefic Roar", "Rose Gold Meteor"), "Full damage assassination"),
                Build("Off-tank", listOf("Tough Boots", "Blade of the Heptaseas", "Dominance Ice", "Blade of Despair", "Immortality", "Athena's Shield"), "Semi-tank")
            ),
            emblem = "Custom Assassin", battleSpell = "Petrify",
            counters = listOf("Kaja", "Ruby", "Esmeralda"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 52.8f, pickRate = 15.6f, banRate = 48.3f, tier = "S"
        ),
        HeroData(
            id = "yuzhong",
            name = "Yu Zhong", nameCn = "于忠",
            role = "Fighter", specialty = "Durable/Chase",
            stats = HeroStats(3350, 290, 168, 172, 3.2f),
            skills = HeroSkills(
                passive = "Cursing Touch — Stacking Sha Essence, heals when full",
                skill1 = "Dragon Tail — AoE spin, heals on hit",
                skill2 = "Soul Grip — Pull + slow, generates Sha Essence",
                ultimate = "Black Dragon Form — Transform into dragon, CC immune"
            ),
            builds = listOf(
                Build("Core", listOf("Warrior Boots", "Blade of the Heptaseas", "Dominance Ice", "Queen's Wings", "Immortality", "Radiant Armor"), "Sustain fighter"),
                Build("Damage", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Dominance Ice", "Queen's Wings", "Immortality"), "Carry potential"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Full tank")
            ),
            emblem = "Custom Fighter", battleSpell = "Petrify",
            counters = listOf("Karrie", "Thamuz", "Esmeralda"),
            synergies = listOf("Angela", "Estes", "Atlas"),
            winRate = 51.5f, pickRate = 13.2f, banRate = 35.4f, tier = "S"
        ),
        HeroData(
            id = "esmeralda",
            name = "Esmeralda", nameCn = "埃斯梅拉达",
            role = "Fighter", specialty = "Sustain/Magic Damage",
            stats = HeroStats(3100, 450, 110, 160, 3.3f),
            skills = HeroSkills(
                passive = "Starmoon Censer — Shield steal from enemies",
                skill1 = "Frostmoon Shield — Gain shield + movement speed",
                skill2 = "Falling Starmoon — AoE magic damage + shield",
                ultimate = "Falling Starmoon — Knock up + pull + AoE magic damage"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Dominance Ice", "Queen's Wings", "Athena's Shield", "Immortality", "Holy Crystal"), "Sustain magic fighter"),
                Build("Damage", listOf("Arcane Boots", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Full damage"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "Magic tank")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Angela", "Estes", "Atlas"),
            winRate = 50.2f, pickRate = 9.8f, banRate = 22.1f, tier = "A"
        ),
        HeroData(
            id = "thamuz",
            name = "Thamuz", nameCn = "塔穆兹",
            role = "Fighter", specialty = "Durable/Damage",
            stats = HeroStats(3400, 260, 180, 170, 3.1f),
            skills = HeroSkills(
                passive = "Cauterant Inferno — Basic attacks deal AoE fire damage",
                skill1 = "Molten Scythes — Throws scythes, pull back",
                skill2 = "Scorch — Leap + AoE fire damage",
                ultimate = "Cauterant Inferno — AoE fire aura + heal"
            ),
            builds = listOf(
                Build("Core", listOf("Warrior Boots", "Dominance Ice", "Queen's Wings", "Blade of Despair", "Immortality", "Athena's Shield"), "Bruiser build"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Full tank frontline")
            ),
            emblem = "Custom Fighter", battleSpell = "Petrify",
            counters = listOf("Karrie", "Claude", "Lylia"),
            synergies = listOf("Angela", "Estes", "Atlas"),
            winRate = 50.8f, pickRate = 8.5f, banRate = 19.3f, tier = "A"
        ),
        HeroData(
            id = "lapu",
            name = "Lapu-Lapu", nameCn = "拉普拉普",
            role = "Fighter", specialty = "Damage/Durable",
            stats = HeroStats(3250, 270, 174, 168, 3.3f),
            skills = HeroSkills(
                passive = "Bravest Fighter — Damage reduction when below 50% HP",
                skill1 = "Justice Blades — Throws 2 boomerangs",
                skill2 = "Brave Stance — Dash + enhanced basic attack",
                ultimate = "Chieftain's Rage — Transform, gains armor + AoE damage"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Blade of the Heptaseas", "Dominance Ice", "Queen's Wings", "Immortality", "Blade of Despair"), "Standard fighter"),
                Build("Damage", listOf("Warrior Boots", "Blade of Despair", "Endless Battle", "Hunter Strike", "Malefic Roar", "Immortality"), "Full damage")
            ),
            emblem = "Custom Fighter", battleSpell = "Flicker",
            counters = listOf("Esmeralda", "Karrie", "Ruby"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 50.1f, pickRate = 7.8f, banRate = 15.2f, tier = "B"
        ),
        HeroData(
            id = "paquito",
            name = "Paquito", nameCn = "帕奎托",
            role = "Fighter", specialty = "Burst/Chase",
            stats = HeroStats(3150, 285, 170, 155, 3.5f),
            skills = HeroSkills(
                passive = "Champ Stance — Enhanced skills after 4 hits",
                skill1 = "Jab — Quick punch + shield",
                skill2 = "Heavy Left Hook — Charging punch + CC",
                ultimate = "Knockout Strike — Dash + knockback + CC chain"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Dominance Ice", "Immortality", "Blade of the Heptaseas"), "Standard bruiser"),
                Build("Damage", listOf("Warrior Boots", "Blade of Despair", "Hunter Strike", "Malefic Roar", "Endless Battle", "Rose Gold Meteor"), "Full damage burst")
            ),
            emblem = "Custom Fighter", battleSpell = "Petrify",
            counters = listOf("Ruby", "Kaja", "Esmeralda"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 51.0f, pickRate = 10.2f, banRate = 25.6f, tier = "A"
        ),
        HeroData(
            id = "terizla",
            name = "Terizla", nameCn = "特里兹拉",
            role = "Fighter", specialty = "Damage/Durable",
            stats = HeroStats(3500, 250, 182, 178, 3.0f),
            skills = HeroSkills(
                passive = "Body of Smith — Damage reduction below 30% HP",
                skill1 = "Revenge Strike — Hammer throw + slow",
                skill2 = "Execution Strike — 3-stage hammer slam",
                ultimate = "Penalty Zone — Large AoE pull + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Queen's Wings", "Blade of Despair", "Immortality", "Athena's Shield"), "Standard build"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Radiant Armor"), "Full tank")
            ),
            emblem = "Custom Fighter", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 49.5f, pickRate = 6.3f, banRate = 12.8f, tier = "B"
        ),
        HeroData(
            id = "fredrinn",
            name = "Fredrinn", nameCn = "弗雷德里",
            role = "Fighter", specialty = "Durable/Tank",
            stats = HeroStats(3600, 240, 165, 185, 3.0f),
            skills = HeroSkills(
                passive = "Crystal Energy — Converts HP to damage on ult",
                skill1 = "Piercing Strike — Line stun + Taunt",
                skill2 = "Brave Assault — Dash + knockup",
                ultimate = "Appraiser's Wrath — Massive single target + heal"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Queen's Wings", "Blade of Despair", "Immortality", "Athena's Shield"), "Bruiser"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Radiant Armor"), "Full tank")
            ),
            emblem = "Custom Tank", battleSpell = "Petrify",
            counters = listOf("Karrie", "Wanwan", "Claude"),
            synergies = listOf("Angela", "Estes", "Atlas"),
            winRate = 51.2f, pickRate = 8.9f, banRate = 20.4f, tier = "A"
        ),
        HeroData(
            id = "arlot",
            name = "Arlott", nameCn = "阿洛特",
            role = "Fighter", specialty = "Chase/Burst",
            stats = HeroStats(3100, 280, 172, 160, 3.4f),
            skills = HeroSkills(
                passive = "Demon Gaze — Enhanced next basic attack after skill",
                skill1 = "Dauntless Strike — AoE knockup + slow",
                skill2 = "Vengeance — Dash + mark + enhanced basic attack",
                ultimate = "Final Slash — Dash + AoE pull + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Blade of Despair", "Endless Battle", "Dominance Ice", "Immortality", "Blade of the Heptaseas"), "Standard burst fighter"),
                Build("Damage", listOf("Warrior Boots", "Blade of Despair", "Hunter Strike", "Malefic Roar", "Endless Battle", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Assassin", battleSpell = "Petrify",
            counters = listOf("Ruby", "Kaja", "Esmeralda"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 50.6f, pickRate = 8.1f, banRate = 21.3f, tier = "A"
        ),
        HeroData(
            id = "silvanna",
            name = "Silvanna", nameCn = "希尔瓦娜",
            role = "Fighter", specialty = "Chase/Magic Damage",
            stats = HeroStats(3050, 420, 120, 155, 3.4f),
            skills = HeroSkills(
                passive = "Cometic Lance — Stacking magic penetration",
                skill1 = "Spiral Strangling — 3-hit magic damage + slow",
                skill2 = "Soul Lock — Dash + magic damage + mark",
                ultimate = "Imperial Justice — Jump + AoE magic damage + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Concentrated Energy", "Dominance Ice", "Holy Crystal", "Divine Glaive", "Immortality"), "Magic fighter"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Full magic damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Angela", "Estes", "Atlas"),
            winRate = 49.8f, pickRate = 6.7f, banRate = 14.5f, tier = "B"
        ),

        // ═══════════════════════════════════════════
        // MAGES
        // ═══════════════════════════════════════════
        HeroData(
            id = "kagura",
            name = "Kagura", nameCn = "神乐",
            role = "Mage", specialty = "Burst/Mobility",
            stats = HeroStats(2400, 480, 115, 95, 3.3f),
            skills = HeroSkills(
                passive = "Yin Yang Overturn — Umbrella enhances skills",
                skill1 = "Seimei Umbrella Open — Throws umbrella, AoE damage",
                skill2 = "Rasho Umbrella Flee — Teleport to umbrella, CC cleanse",
                ultimate = "Yin Yang Overturn — Umbrella pulls enemies, AoE burst"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Full damage"),
                Build("Sustain", listOf("Arcane Boots", "Concentrated Energy", "Lightning Truncheon", "Winter Truncheon", "Immortality", "Athena's Shield"), "Survival mage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Eudora"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 52.1f, pickRate = 16.4f, banRate = 42.8f, tier = "S"
        ),
        HeroData(
            id = "pharsa",
            name = "Pharsa", nameCn = "帕尔萨",
            role = "Mage", specialty = "Burst/Poke",
            stats = HeroStats(2350, 500, 120, 90, 3.2f),
            skills = HeroSkills(
                passive = "Raven — Enhanced basic attack after skills",
                skill1 = "Curse of Crow — AoE slow + damage over time",
                skill2 = "Wings by Wings — Transform into bird, fly to location",
                ultimate = "Feathered Air Strike — 4 massive AoE bombardments"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Standard burst"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage + sustain"),
                Build("Poke", listOf("Arcane Boots", "Enchanted Talisman", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "CDR poke")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Saber", "Fanny", "Ling"),
            synergies = listOf("Atlas", "Kaja", "Tigreal"),
            winRate = 51.8f, pickRate = 12.1f, banRate = 32.5f, tier = "A"
        ),
        HeroData(
            id = "eudora",
            name = "Eudora", nameCn = "尤朵拉",
            role = "Mage", specialty = "Burst",
            stats = HeroStats(2380, 490, 118, 92, 3.1f),
            skills = HeroSkills(
                passive = "Superconductor — Enhanced next skill on marked targets",
                skill1 = "Forked Lightning — Line AoE magic damage",
                skill2 = "Ball of Lightning — Single target stun + damage",
                ultimate = "Thunder's Wrath — Massive single target burst + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Standard burst mage"),
                Build("Full Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality), "Maximum burst on squishies")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kagura", "Lunox", "Esmeralda"),
            synergies = listOf("Atlas", "Kaja", "Tigreal"),
            winRate = 50.5f, pickRate = 11.8f, banRate = 28.9f, tier = "A"
        ),
        HeroData(
            id = "lunox",
            name = "Lunox", nameCn = "露诺克斯",
            role = "Mage", specialty = "Burst/Sustain",
            stats = HeroStats(2420, 510, 125, 98, 3.2f),
            skills = HeroSkills(
                passive = "Starsoul — Light side heals, Dark side damages",
                skill1 = "Starlight Pulse — Light AoE heal + damage",
                skill2 = "Chaos Assault — Dark single target burst",
                ultimate = "Order & Chaos — Switch between invincibility modes"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Standard burst + sustain"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 51.5f, pickRate = 10.3f, banRate = 30.2f, tier = "A"
        ),
        HeroData(
            id = "lylia",
            name = "Lylia", nameCn = "莉莉娅",
            role = "Mage", specialty = "Poke/Damage",
            stats = HeroStats(2300, 470, 112, 88, 3.4f),
            skills = HeroSkills(
                passive = "Magic Ambush — Gains movement speed when low HP",
                skill1 = "Magic Shockwave — Line AoE slow + damage",
                skill2 = "Shadow Energy — Throws bomb, can chain explosions",
                ultimate = "Black Shoes — Teleport back to position 4s ago, heal"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Esmeralda", "Ruby", "Kaja"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 50.8f, pickRate = 9.5f, banRate = 22.7f, tier = "A"
        ),
        HeroData(
            id = "valentina",
            name = "Valentina", nameCn = "瓦伦蒂娜",
            role = "Mage", specialty = "Burst/Utility",
            stats = HeroStats(2450, 460, 122, 96, 3.3f),
            skills = HeroSkills(
                passive = "Blood Ancestry — Gains magic power per level",
                skill1 = "Arcane Shade — Line damage + slow + mark",
                skill2 = "Shadow Strike — Dash + AoE magic damage",
                ultimate = "I Am You — Copy enemy's ultimate"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Standard burst"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 51.2f, pickRate = 11.6f, banRate = 33.8f, tier = "A"
        ),
        HeroData(
            id = "faramis",
            name = "Faramis", nameCn = "法拉米斯",
            role = "Mage", specialty = "Support/Magic Damage",
            stats = HeroStats(2500, 480, 115, 100, 3.2f),
            skills = HeroSkills(
                passive = "Shadow Stampede — Gains movement speed after skill",
                skill1 = "Ghost Bursters — Line damage + pull",
                skill2 = "Shadow Tear — AoE slow + magic damage",
                ultimate = "Cult Altar — Revive nearby dead allies"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Enchanted Talisman", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard mage/support"),
                Build("Utility", listOf("Arcane Boots", "Enchanted Talisman", "Lightning Truncheon", "Dominance Ice", "Immortality", "Winter Truncheon"), "Utility support")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Tigreal", "Angela"),
            winRate = 50.8f, pickRate = 8.4f, banRate = 25.1f, tier = "A"
        ),
        HeroData(
            id = "xavier",
            name = "Xavier", nameCn = "泽维尔",
            role = "Mage", specialty = "Poke/Burst",
            stats = HeroStats(2380, 475, 118, 93, 3.2f),
            skills = HeroSkills(
                passive = "Dawning Light — Enhanced skills as battle progresses",
                skill1 = "Infinite Extension — Line damage + slow",
                skill2 = "Mystic Field — AoE barrier + damage",
                ultimate = "Dawning Light — Global beam, massive damage"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Kaja", "Estes"),
            winRate = 50.2f, pickRate = 7.9f, banRate = 18.6f, tier = "B"
        ),
        HeroData(
            id = "celestrian",
            name = "Cecilion", nameCn = "塞西利翁",
            role = "Mage", specialty = "Poke/Burst",
            stats = HeroStats(2400, 495, 122, 94, 3.1f),
            skills = HeroSkills(
                passive = "Blood Feast — Gains permanent magic power per minion",
                skill1 = "Bat Impact — Line damage, enhanced at max stacks",
                skill2 = "Sanguine Claws — Pull + damage",
                ultimate = "Crimson Flower — AoE life steal aura"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Standard scaling mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 50.5f, pickRate = 7.2f, banRate = 16.8f, tier = "B"
        ),

        // ═══════════════════════════════════════════
        // MARKSMEN
        // ═══════════════════════════════════════════
        HeroData(
            id = "granger",
            name = "Granger", nameCn = "格兰杰",
            role = "Marksman", specialty = "Burst/Jungle",
            stats = HeroStats(2350, 310, 155, 85, 3.2f),
            skills = HeroSkills(
                passive = "Caprice — Enhanced 6th bullet deals critical damage",
                skill1 = "Rhapsody — Rapid fire, immune to CC",
                skill2 = "Rondo — Dash + reload bullets",
                ultimate = "Death Sonata — 3 long range shots, execute damage"
            ),
            builds = listOf(
                Build("Core", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Blade of the Heptaseas", "Malefic Roar", "Immortality"), "Standard burst jungle"),
                Build("Damage", listOf("Tough Boots", "Blade of Despair", "Hunter Strike", "Malefic Roar", "Blade of the Heptaseas", "Rose Gold Meteor"), "Full damage"),
                Build("Sustain", listOf("Magic Shoes", "Blade of Despair", "Endless Battle", "Immortality", "Athena's Shield", "Malefic Roar"), "Survival build")
            ),
            emblem = "Custom Marksman", battleSpell = "Retribution",
            counters = listOf("Chou", "Lolita", "Kaja"),
            synergies = listOf("Kaja", "Atlas", "Angela"),
            winRate = 53.2f, pickRate = 18.5f, banRate = 45.8f, tier = "S"
        ),
        HeroData(
            id = "miya",
            name = "Miya", nameCn = "米雅",
            role = "Marksman", specialty = "Damage/Jungle",
            stats = HeroStats(2280, 290, 148, 82, 3.3f),
            skills = HeroSkills(
                passive = "Moon Blessing — Attack speed + movement speed stacks",
                skill1 = "Moon Arrow — Enhanced basic attack, multiple targets",
                skill2 = "Arrow of Eclipse — AoE slow + root",
                ultimate = "Hidden Moonlight — Purify + invisibility + movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Windtalker", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Immortality"), "Standard DPS"),
                Build("Damage", listOf("Swift Boots", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Windtalker", "Rose Gold Meteor"), "Full damage"),
                Build("Sustain", listOf("Tough Boots", "Windtalker", "Berserker's Fury", "Immortality", "Athena's Shield", "Malefic Roar"), "Survival DPS")
            ),
            emblem = "Custom Marksman", battleSpell = "Retribution",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Kaja", "Angela"),
            winRate = 51.8f, pickRate = 14.2f, banRate = 35.2f, tier = "A"
        ),
        HeroData(
            id = "wanwan",
            name = "Wanwan", nameCn = "婉婉",
            role = "Marksman", specialty = "Burst/Mobility",
            stats = HeroStats(2320, 300, 152, 84, 3.5f),
            skills = HeroSkills(
                passive = "Tiger Pace — Jump after basic attack",
                skill1 = "Swallow's Path — Throws dagger, stuns on return",
                skill2 = "Needles in Flowers — Cleanses CC",
                ultimate = "Crossbow of Tang — Untargetable, rapid fire arrows"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Demon Hunter Sword", "Berserker's Fury", "Windtalker", "Malefic Roar", "Immortality"), "Standard mobility DPS"),
                Build("Damage", listOf("Swift Boots", "Demon Hunter Sword", "Berserker's Fury", "Malefic Roar", "Windtalker", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Lolita", "Kaja", "Ruby"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 52.5f, pickRate = 13.8f, banRate = 40.2f, tier = "S"
        ),
        HeroData(
            id = "claude",
            name = "Claude", nameCn = "克劳德",
            role = "Marksman", specialty = "Damage/Jungle",
            stats = HeroStats(2300, 295, 150, 83, 3.4f),
            skills = HeroSkills(
                passive = "Battle Side-by-Side — Dexter enhances basic attack",
                skill1 = "Art of Thievery — Steals movement speed + attack speed",
                skill2 = "Battle Mirror Image — Creates clone, swap positions",
                ultimate = "Blazing Duet — AoE rapid fire + movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Demon Hunter Sword", "Golden Staff", "Corrosion Scythe", "Malefic Roar", "Immortality"), "Standard jungle DPS"),
                Build("Damage", listOf("Swift Boots", "Demon Hunter Sword", "Golden Staff", "Malefic Roar", "Corrosion Scythe", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Retribution",
            counters = listOf("Kaja", "Saber", "Lolita"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 51.2f, pickRate = 11.5f, banRate = 28.8f, tier = "A"
        ),
        HeroData(
            id = "brody",
            name = "Brody", nameCn = "布罗迪",
            role = "Marksman", specialty = "Burst",
            stats = HeroStats(2380, 320, 160, 88, 3.1f),
            skills = HeroSkills(
                passive = "Abyssal Strike — Enhanced basic attack deals more with distance",
                skill1 = "Abyssal Impact — Line damage + mark",
                skill2 = "Corrosive Strike — Dash + CC",
                ultimate = "Torn-Apart Memory — Execute based on missing HP"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Blade of Despair", "Endless Battle", "Berserker's Fury", "Malefic Roar", "Immortality"), "Standard burst marksman"),
                Build("Damage", listOf("Swift Boots", "Blade of Despair", "Hunter Strike", "Berserker's Fury", "Malefic Roar", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Kaja", "Angela"),
            winRate = 50.8f, pickRate = 9.4f, banRate = 22.5f, tier = "B"
        ),
        HeroData(
            id = "moskov",
            name = "Moskov", nameCn = "莫斯科夫",
            role = "Marksman", specialty = "Damage",
            stats = HeroStats(2340, 305, 156, 86, 3.2f),
            skills = HeroSkills(
                passive = "Spear of Quiescence — Basic attack has chance to pierce",
                skill1 = "Abyss Walker — Teleport + attack speed boost",
                skill2 = "Spear of Misery — Knockback + stun if hits wall",
                ultimate = "Spear of Destruction — Global line damage"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Windtalker", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Immortality"), "Standard DPS"),
                Build("Damage", listOf("Swift Boots", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Windtalker", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.8f, pickRate = 7.6f, banRate = 15.2f, tier = "B"
        ),
        HeroData(
            id = "lesley",
            name = "Lesley", nameCn = "莱斯利",
            role = "Marksman", specialty = "Burst",
            stats = HeroStats(2260, 295, 153, 81, 3.4f),
            skills = HeroSkills(
                passive = "Master of Camouflage — Enhanced basic attack from stealth",
                skill1 = "Master of Camouflage — Enter stealth + movement speed",
                skill2 = "Tactical Grenade — Knockback + slow",
                ultimate = "Ultimate Snipe — 3 long range shots, execute damage"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Blade of Despair", "Endless Battle", "Berserker's Fury", "Malefic Roar", "Immortality"), "Standard burst"),
                Build("Damage", listOf("Swift Boots", "Blade of Despair", "Hunter Strike", "Berserker's Fury", "Malefic Roar", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 50.2f, pickRate = 8.3f, banRate = 18.9f, tier = "B"
        ),
        HeroData(
            id = "popol",
            name = "Popol and Kupa", nameCn = "波波尔和库帕",
            role = "Marksman", specialty = "Push/Poke",
            stats = HeroStats(2300, 300, 145, 80, 3.3f),
            skills = HeroSkills(
                passive = "Popol & Kupa — Kupa fights alongside Popol",
                skill1 = "Kupa, Help! — Kupa bites enemy, stuns",
                skill2 = "Popol, Attack! — Enhanced basic attack + trap",
                ultimate = "Popol and Kupa, Together! — Kupa transforms, AoE damage"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Demon Hunter Sword", "Windtalker", "Malefic Roar", "Berserker's Fury", "Immortality"), "Standard DPS"),
                Build("Push", listOf("Swift Boots", "Demon Hunter Sword", "Golden Staff", "Corrosion Scythe", "Malefic Roar", "Immortality"), "Split push")
            ),
            emblem = "Custom Marksman", battleSpell = "Retribution",
            counters = listOf("Fanny", "Saber", "Lolita"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.5f, pickRate = 6.8f, banRate = 12.4f, tier = "B"
        ),
        HeroData(
            id = "irithel",
            name = "Irithel", nameCn = "伊瑞斯",
            role = "Marksman", specialty = "Damage",
            stats = HeroStats(2320, 298, 154, 84, 3.2f),
            skills = HeroSkills(
                passive = "Fierce roar — Can move while shooting",
                skill1 = "Strafe — AoE damage + slow",
                skill2 = "Force of the Queen — Enhanced basic attack + attack speed",
                ultimate = "Heavy Crossbow — AoE rapid fire + movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Windtalker", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Immortality"), "Standard DPS"),
                Build("Damage", listOf("Swift Boots", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Windtalker", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.2f, pickRate = 6.2f, banRate = 11.8f, tier = "B"
        ),
        HeroData(
            id = "hanabi",
            name = "Hanabi", nameCn = "花吹",
            role = "Marksman", specialty = "Damage",
            stats = HeroStats(2280, 292, 150, 82, 3.3f),
            skills = HeroSkills(
                passive = "Ninjutsu: Petal Barrage — Bouncing basic attack",
                skill1 = "Ninjutsu: Soul Scroll — Enhanced basic attack + slow",
                skill2 = "Ninjutsu: Petal Storm — AoE damage + slow",
                ultimate = "Forbidden Jutsu: Higanbana — AoE root + damage over time"
            ),
            builds = listOf(
                Build("Core", listOf("Swift Boots", "Windtalker", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Immortality"), "Standard DPS"),
                Build("Damage", listOf("Swift Boots", "Berserker's Fury", "Demon Hunter Sword", "Malefic Roar", "Windtalker", "Rose Gold Meteor"), "Full damage")
            ),
            emblem = "Custom Marksman", battleSpell = "Flicker",
            counters = listOf("Fanny", "Ling", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 48.8f, pickRate = 5.9f, banRate = 10.5f, tier = "C"
        ),

        // ═══════════════════════════════════════════
        // TANKS
        // ═══════════════════════════════════════════
        HeroData(
            id = "tigreal",
            name = "Tigreal", nameCn = "泰格瑞尔",
            role = "Tank", specialty = "Crowd Control",
            stats = HeroStats(3800, 280, 120, 210, 2.8f),
            skills = HeroSkills(
                passive = "Holy Guard — Shield when near allies",
                skill1 = "Attack Wave — Line damage + slow",
                skill2 = "Sacred Hammer — Knockup + CC",
                ultimate = "Implosion — Pulls all enemies to center + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy"),
                Build("Physical Tank", listOf("Tough Boots", "Dominance Ice", "Antique Cuirass", "Blade Armor", "Immortality", "Athena's Shield"), "vs Physical heavy"),
                Build("Support Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass", "Conceal Roam"), "Roaming tank")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 50.5f, pickRate = 12.8f, banRate = 25.4f, tier = "A"
        ),
        HeroData(
            id = "atlas",
            name = "Atlas", nameCn = "阿特拉斯",
            role = "Tank", specialty = "Crowd Control",
            stats = HeroStats(3900, 270, 115, 215, 2.7f),
            skills = HeroSkills(
                passive = "Frigid Breath — Frozen aura slows enemies",
                skill1 = "Perfect Match — Jump + AoE stun",
                skill2 = "Pilot's Grace — Dash + slow",
                ultimate = "Fatal Links — Pulls 5 enemies to a location"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy"),
                Build("Physical Tank", listOf("Tough Boots", "Dominance Ice", "Antique Cuirass", "Blade Armor", "Immortality", "Athena's Shield"), "vs Physical heavy")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 52.8f, pickRate = 16.2f, banRate = 42.5f, tier = "S"
        ),
        HeroData(
            id = "khufra",
            name = "Khufra", nameCn = "库弗拉",
            role = "Tank", specialty = "Crowd Control",
            stats = HeroStats(3750, 275, 118, 208, 2.9f),
            skills = HeroSkills(
                passive = "Wrath Sanction — Enhanced basic attack + heal",
                skill1 = "Tyrant's Revenge — Jump + knockup + stun",
                skill2 = "Bouncing Ball — Bounces, immune to dash",
                ultimate = "Tyrant's Rage — Knockback + stun if hits wall"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 51.5f, pickRate = 10.5f, banRate = 28.3f, tier = "A"
        ),
        HeroData(
            id = "franco",
            name = "Franco", nameCn = "弗朗哥",
            role = "Tank", specialty = "Crowd Control/Pick-off",
            stats = HeroStats(3650, 285, 125, 205, 2.9f),
            skills = HeroSkills(
                passive = "Legend of the Iron Hook — Gains movement speed",
                skill1 = "Iron Hook — Long range hook, pulls enemy",
                skill2 = "Fury Shock — AoE slow + damage",
                ultimate = "Bloody Hunt — Suppress target for 1.8s"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Roam", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Roaming hook")
            ),
            emblem = "Custom Tank", battleSpell = "Petrify",
            counters = listOf("Kagura", "Lunox", "Lylia"),
            synergies = listOf("Granger", "Miya", "Eudora"),
            winRate = 50.2f, pickRate = 8.8f, banRate = 22.6f, tier = "A"
        ),
        HeroData(
            id = "johnson",
            name = "Johnson", nameCn = "约翰逊",
            role = "Tank", specialty = "Initiator",
            stats = HeroStats(3850, 280, 118, 212, 2.8f),
            skills = HeroSkills(
                passive = "Electromag Rays — Shield when low HP",
                skill1 = "Deadly Pincers — Line stun + damage",
                skill2 = "Electromag Rays — AoE magic damage",
                ultimate = "Rapid Touchdown — Drive car, AoE stun on crash"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Kagura", "Lunox", "Esmeralda"),
            synergies = listOf("Odette", "Kagura", "Eudora"),
            winRate = 49.8f, pickRate = 7.5f, banRate = 18.2f, tier = "B"
        ),
        HeroData(
            id = "lolita",
            name = "Lolita", nameCn = "洛丽塔",
            role = "Tank", specialty = "Support",
            stats = HeroStats(3700, 290, 112, 208, 2.8f),
            skills = HeroSkills(
                passive = "Maiden's Blessing — Shield for nearby allies",
                skill1 = "Guardian's Blessing — Dash + enhanced basic attack + stun",
                skill2 = "Guardian's Bulwark — Blocks projectiles",
                ultimate = "Noumenon Blast — AoE stun + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank support"),
                Build("Support", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Roaming support")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 50.8f, pickRate = 8.2f, banRate = 20.5f, tier = "A"
        ),
        HeroData(
            id = "grock",
            name = "Grock", nameCn = "格洛克",
            role = "Tank", specialty = "Damage/Initiator",
            stats = HeroStats(3950, 275, 130, 220, 2.7f),
            skills = HeroSkills(
                passive = "Ancestral Gift — Gains shield + damage near walls",
                skill1 = "Power of Nature — Channel + AoE damage + CC immune",
                skill2 = "Guardian's Barrier — Creates wall, blocks enemies",
                ultimate = "Wild Charge — Dash + knockback + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Damage", listOf("Warrior Boots", "Blade of Despair", "Dominance Ice", "Queen's Wings", "Immortality", "Athena's Shield"), "Damage tank")
            ),
            emblem = "Custom Tank", battleSpell = "Petrify",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 49.5f, pickRate = 6.8f, banRate = 15.4f, tier = "B"
        ),
        HeroData(
            id = "minotaur",
            name = "Minotaur", nameCn = "米诺陶",
            role = "Tank", specialty = "Support",
            stats = HeroStats(3800, 280, 115, 210, 2.8f),
            skills = HeroSkills(
                passive = "Rage — Gains rage from damage, transforms when full",
                skill1 = "Despair Stomp — AoE slow + damage",
                skill2 = "Motivation Roar — Heal nearby allies",
                ultimate = "Minoan Fury — Transform + knockup + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank support"),
                Build("Support", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Roaming support")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 49.2f, pickRate = 6.5f, banRate = 14.8f, tier = "B"
        ),
        HeroData(
            id = "akai",
            name = "Akai", nameCn = "阿凯",
            role = "Tank", specialty = "Crowd Control",
            stats = HeroStats(3750, 278, 120, 208, 2.9f),
            skills = HeroSkills(
                passive = "Tai Chi — Enhanced basic attack, gains shield",
                skill1 = "Headbutt — Line damage + stun",
                skill2 = "Blender — AoE slow + damage",
                ultimate = "Heavy Spin — Spin + knockback + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Support", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Roaming tank")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 48.8f, pickRate = 5.9f, banRate = 12.2f, tier = "C"
        ),
        HeroData(
            id = "belerick",
            name = "Belerick", nameCn = "贝莱里克",
            role = "Tank", specialty = "Durable/Support",
            stats = HeroStats(4000, 260, 108, 225, 2.6f),
            skills = HeroSkills(
                passive = "Ancient Wrath — Reflects damage to attackers",
                skill1 = "Wrath of Dryad — AoE slow + damage",
                skill2 = "Nature's Strike — Dash + root",
                ultimate = "Wrath of Dryad — AoE root + damage over time"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 48.5f, pickRate = 5.2f, banRate = 10.8f, tier = "C"
        ),
        HeroData(
            id = " uranus",
            name = "Uranus", nameCn = "乌拉诺斯",
            role = "Tank", specialty = "Durable",
            stats = HeroStats(4100, 250, 110, 230, 2.6f),
            skills = HeroSkills(
                passive = "Radiance — Regenerates HP over time",
                skill1 = "Ionic Edge — Line damage + slow",
                skill2 = "Transcendent Ward — Shield + movement speed",
                ultimate = "Supernova — AoE stun + knockback"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Standard tank"),
                Build("Magic Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Radiant Armor", "Immortality", "Antique Cuirass"), "vs Magic heavy")
            ),
            emblem = "Custom Tank", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Granger", "Miya", "Kagura"),
            winRate = 48.2f, pickRate = 4.8f, banRate = 9.5f, tier = "C"
        ),
        HeroData(
            id = "balmond",
            name = "Balmond", nameCn = "巴尔蒙德",
            role = "Tank", specialty = "Durable/Damage",
            stats = HeroStats(3700, 265, 135, 200, 2.9f),
            skills = HeroSkills(
                passive = "Lethal Counter — Kill grants HP regen",
                skill1 = "Soul Lock — Line damage + slow",
                skill2 = "Cyclone Sweep — AoE spin damage",
                ultimate = "Lethal Counter — Execute damage based on missing HP"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Queen's Wings", "Blade of Despair", "Immortality", "Athena's Shield"), "Bruiser tank"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Full tank")
            ),
            emblem = "Custom Fighter", battleSpell = "Petrify",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.0f, pickRate = 5.5f, banRate = 11.2f, tier = "C"
        ),
        HeroData(
            id = "hilda",
            name = "Hilda", nameCn = "希尔达",
            role = "Tank", specialty = "Durable/Initiator",
            stats = HeroStats(3650, 270, 128, 205, 3.0f),
            skills = HeroSkills(
                passive = "Blessing of Goddess — Regen in bush + speed",
                skill1 = "Spiraling Strikes — 3-hit combo + slow",
                skill2 = "Mortal Coil — AoE damage + root",
                ultimate = "Warrior's Fury — AoE knockback + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Dominance Ice", "Queen's Wings", "Blade of Despair", "Immortality", "Athena's Shield"), "Bruiser"),
                Build("Tank", listOf("Tough Boots", "Dominance Ice", "Athena's Shield", "Antique Cuirass", "Immortality", "Blade Armor"), "Full tank")
            ),
            emblem = "Custom Tank", battleSpell = "Petrify",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 48.8f, pickRate = 5.2f, banRate = 10.5f, tier = "C"
        ),

        // ═══════════════════════════════════════════
        // SUPPORTS
        // ═══════════════════════════════════════════
        HeroData(
            id = "angela",
            name = "Angela", nameCn = "安吉拉",
            role = "Support", specialty = "Healer",
            stats = HeroStats(2200, 480, 65, 90, 3.0f),
            skills = HeroSkills(
                passive = "Sorrowful Whispers — Heals target + herself",
                skill1 = "Love Waves — Heal ally + damage enemy",
                skill2 = "Puppet-on-a-String — Slow + stun",
                ultimate = "Heartguard — Attach to ally, gain shield + movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Fleeting Time", "Athena's Shield", "Immortality", "Dominance Ice"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Fleeting Time", "Athena's Shield", "Immortality"), "Maximum healing")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Chou", "Fanny", "Ling"),
            winRate = 52.5f, pickRate = 14.8f, banRate = 42.2f, tier = "S"
        ),
        HeroData(
            id = "estes",
            name = "Estes", nameCn = "埃斯特斯",
            role = "Support", specialty = "Healer",
            stats = HeroStats(2350, 450, 70, 95, 2.9f),
            skills = HeroSkills(
                passive = "Moon Elf Immortal — Enhanced basic attack heals",
                skill1 = "Moonlight Immersion — Link ally, heal over time",
                skill2 = "Domain of Moon Goddess — AoE slow + heal",
                ultimate = "Blessing of Moon Goddess — Massive AoE heal + damage reduction"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Fleeting Time", "Athena's Shield", "Immortality"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Dominance Ice", "Athena's Shield", "Immortality"), "Maximum sustain")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Yu Zhong", "Esmeralda", "Atlas"),
            winRate = 51.8f, pickRate = 11.2f, banRate = 35.8f, tier = "A"
        ),
        HeroData(
            id = "carmilla",
            name = "Carmilla", nameCn = "卡米拉",
            role = "Support", specialty = "Crowd Control",
            stats = HeroStats(2800, 380, 95, 130, 3.1f),
            skills = HeroSkills(
                passive = "Curse of Blood — Stacking magic damage + slow",
                skill1 = "Bloodbath — AoE damage + heal",
                skill2 = "Curse of Blood — Mark enemy, link to allies",
                ultimate = "Curse of Blood — AoE pull + stun"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Standard support tank"),
                Build("Damage", listOf("Arcane Boots", "Concentrated Energy", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Magic damage support")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Karrie", "Claude", "Wanwan"),
            synergies = listOf("Atlas", "Tigreal", "Kagura"),
            winRate = 50.5f, pickRate = 8.5f, banRate = 22.8f, tier = "A"
        ),
        HeroData(
            id = "nana",
            name = "Nana", nameCn = "娜娜",
            role = "Support", specialty = "Poke/Utility",
            stats = HeroStats(2400, 460, 105, 88, 3.2f),
            skills = HeroSkills(
                passive = "Molina's Gift — Transforms when killed",
                skill1 = "Magic Boomerang — Line AoE damage",
                skill2 = "Molina — Throws morphing creature, transforms enemy",
                ultimate = "Molina's Gift — AoE stun + damage over time"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Magic support"),
                Build("Utility", listOf("Arcane Boots", "Enchanted Talisman", "Lightning Truncheon", "Athena's Shield", "Immortality", "Antique Cuirass"), "Utility poke")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Tigreal", "Kagura"),
            winRate = 50.2f, pickRate = 7.8f, banRate = 18.5f, tier = "B"
        ),
        HeroData(
            id = "mathilda",
            name = "Mathilda", nameCn = "玛蒂尔达",
            role = "Support", specialty = "Mobility/Heal",
            stats = HeroStats(2550, 420, 88, 105, 3.4f),
            skills = HeroSkills(
                passive = "Soul Bloom — Gains shield when near allies",
                skill1 = "Soul Bloom — Enhanced basic attack + mark",
                skill2 = "Guiding Wind — Dash + heal ally",
                ultimate = "Circling Eagle — Fly + AoE damage + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Athena's Shield", "Immortality", "Antique Cuirass"), "Maximum sustain")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Granger", "Kagura"),
            winRate = 50.8f, pickRate = 9.2f, banRate = 24.5f, tier = "A"
        ),
        HeroData(
            id = "rafaela",
            name = "Rafaela", nameCn = "拉斐拉",
            role = "Support", specialty = "Healer",
            stats = HeroStats(2380, 475, 72, 98, 3.1f),
            skills = HeroSkills(
                passive = "Holy Healing — Heal after skills",
                skill1 = "Holy Light — Heal ally + damage enemy",
                skill2 = "Light of Retribution — AoE slow + damage",
                ultimate = "Holy Baptism — Line stun + damage"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Athena's Shield", "Immortality", "Antique Cuirass"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Dominance Ice", "Athena's Shield", "Immortality"), "Maximum healing")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Tigreal", "Kagura"),
            winRate = 49.8f, pickRate = 6.5f, banRate = 15.2f, tier = "B"
        ),
        HeroData(
            id = "diggie",
            name = "Diggie", nameCn = "迪吉",
            role = "Support", specialty = "Utility",
            stats = HeroStats(2300, 455, 68, 92, 3.3f),
            skills = HeroSkills(
                passive = "Clone Tech — Gains shield when low HP",
                skill1 = "Auto Alarm Bomb — Throws bombs, AoE slow + damage",
                skill2 = "Reverse Time — Pull enemy back to original position",
                ultimate = "Time Journey — AoE cleanse + shield + movement speed"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Dominance Ice", "Athena's Shield", "Immortality", "Antique Cuirass"), "Standard support"),
                Build("Utility", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Athena's Shield", "Immortality", "Antique Cuirass"), "Utility support")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Tigreal", "Kagura"),
            winRate = 49.5f, pickRate = 5.8f, banRate = 12.8f, tier = "B"
        ),
        HeroData(
            id = "floryn",
            name = "Floryn", nameCn = "芙洛林",
            role = "Support", specialty = "Healer",
            stats = HeroStats(2350, 470, 70, 95, 3.0f),
            skills = HeroSkills(
                passive = "Bloom — Gains magic power per level",
                skill1 = "Sprocket — Line damage + slow",
                skill2 = "Seed — AoE damage + root",
                ultimate = "Bloom — Global heal for all allies"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Athena's Shield", "Immortality", "Antique Cuirass"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Dominance Ice", "Athena's Shield", "Immortality"), "Maximum healing")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Tigreal", "Kagura"),
            winRate = 49.2f, pickRate = 5.5f, banRate = 11.5f, tier = "B"
        ),
        HeroData(
            id = "estes",
            name = "Estes", nameCn = "埃斯特斯",
            role = "Support", specialty = "Healer",
            stats = HeroStats(2350, 450, 70, 95, 2.9f),
            skills = HeroSkills(
                passive = "Moon Elf Immortal — Enhanced basic attack heals",
                skill1 = "Moonlight Immersion — Link ally, heal over time",
                skill2 = "Domain of Moon Goddess — AoE slow + heal",
                ultimate = "Blessing of Moon Goddess — Massive AoE heal + damage reduction"
            ),
            builds = listOf(
                Build("Core", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Fleeting Time", "Athena's Shield", "Immortality"), "Standard support"),
                Build("Heal", listOf("Tough Boots", "Conceal Roam", "Enchanted Talisman", "Dominance Ice", "Athena's Shield", "Immortality"), "Maximum sustain")
            ),
            emblem = "Custom Support", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Yu Zhong", "Esmeralda", "Atlas"),
            winRate = 51.8f, pickRate = 11.2f, banRate = 35.8f, tier = "A"
        ),
        HeroData(
            id = "cyclops",
            name = "Cyclops", nameCn = "独眼巨人",
            role = "Mage", specialty = "Poke/Mobility",
            stats = HeroStats(2380, 485, 116, 94, 3.3f),
            skills = HeroSkills(
                passive = "Starlit Hourglass — CD reduction per skill hit",
                skill1 = "Stardust Shock — Line AoE damage",
                skill2 = "Planets Attack — Auto-targeting orbs + movement speed",
                ultimate = "Star Power Lockdown — Single target immobilize + damage"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Kaja", "Estes"),
            winRate = 49.8f, pickRate = 7.2f, banRate = 15.8f, tier = "B"
        ),
        HeroData(
            id = "zhask",
            name = "Zhask", nameCn = "扎斯克",
            role = "Mage", specialty = "Push/Poke",
            stats = HeroStats(2420, 490, 120, 96, 3.1f),
            skills = HeroSkills(
                passive = "Mind Eater — Nightmarish Spawn enhanced",
                skill1 = "Nightmarish Spawn — Summon spawn, attacks enemies",
                skill2 = "Mind Eater — Line damage + stun",
                ultimate = "Dominator's Descent — Transform spawn, AoE damage"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Push", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Concentrated Energy", "Winter Truncheon"), "Push focused")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.5f, pickRate = 6.8f, banRate = 14.2f, tier = "B"
        ),
        HeroData(
            id = "harith",
            name = "Harith", nameCn = "哈里斯",
            role = "Mage", specialty = "Burst/Mobility",
            stats = HeroStats(2450, 475, 125, 98, 3.4f),
            skills = HeroSkills(
                passive = "Key of Time — CD reduction when low HP",
                skill1 = "Synchro Fission — Line AoE magic damage",
                skill2 = "Chrono Dash — Dash + enhanced basic attack",
                ultimate = "Zaman Force — Zone that resets skill 2 CD"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Franco"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 50.8f, pickRate = 9.5f, banRate = 22.8f, tier = "A"
        ),
        HeroData(
            id = "change",
            name = "Chang'e", nameCn = "嫦娥",
            role = "Mage", specialty = "Poke/Damage",
            stats = HeroStats(2350, 480, 115, 90, 3.3f),
            skills = HeroSkills(
                passive = "Starlit Hourglass — Enhanced basic attack after skill",
                skill1 = "Stardust Shock — Line AoE damage",
                skill2 = "Meteor Attack — Rapid fire magic projectiles",
                ultimate = "Orbiting Moon — AoE damage + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.2f, pickRate = 6.5f, banRate = 12.8f, tier = "B"
        ),
        HeroData(
            id = "vale",
            name = "Vale", nameCn = "瓦尔",
            role = "Mage", specialty = "Burst",
            stats = HeroStats(2400, 485, 122, 95, 3.2f),
            skills = HeroSkills(
                passive = "Wind Talk — Gains movement speed after skills",
                skill1 = "Wind Blade — Line AoE damage",
                skill2 = "Windstorm — AoE knockup + damage",
                ultimate = "Windstorm — Large AoE damage + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Angela", "Estes"),
            winRate = 49.5f, pickRate = 6.2f, banRate = 11.5f, tier = "B"
        ),
        HeroData(
            id = "aurora",
            name = "Aurora", nameCn = "极光",
            role = "Mage", specialty = "Burst/Control",
            stats = HeroStats(2380, 478, 120, 93, 3.1f),
            skills = HeroSkills(
                passive = "Bitter Frost — Enhanced basic attack freezes",
                skill1 = "Frost Shock — Line AoE freeze + damage",
                skill2 = "Bitter Frost — Single target freeze",
                ultimate = "Cold Destruction — AoE freeze + massive damage"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Immortality"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Tigreal", "Estes"),
            winRate = 49.8f, pickRate = 7.8f, banRate = 16.2f, tier = "B"
        ),
        HeroData(
            id = "odette",
            name = "Odette", nameCn = "奥黛特",
            role = "Mage", specialty = "Burst/AoE",
            stats = HeroStats(2400, 475, 118, 92, 3.2f),
            skills = HeroSkills(
                passive = "Power of the Swan — Enhanced basic attack after skill",
                skill1 = "Avian Authority — Line AoE damage",
                skill2 = "Blue Nova — Single target damage + stun",
                ultimate = "Swan Song — Massive AoE damage + slow"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Winter Truncheon", "Immortality"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Johnson", "Atlas", "Angela"),
            winRate = 48.8f, pickRate = 6.5f, banRate = 13.5f, tier = "B"
        ),
        HeroData(
            id = " gord",
            name = "Gord", nameCn = "戈尔德",
            role = "Mage", specialty = "Poke/Damage",
            stats = HeroStats(2360, 472, 114, 89, 3.3f),
            skills = HeroSkills(
                passive = "Mystic Pursuit — Enhanced basic attack after skill",
                skill1 = "Mystic Projectile — Line AoE damage",
                skill2 = "Mystic Injunction — AoE slow + damage over time",
                ultimate = "Mystic Gush — Continuous line beam"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Enchanted Talisman", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard poke mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Concentrated Energy", "Winter Truncheon"), "Full damage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Ling", "Fanny", "Saber"),
            synergies = listOf("Atlas", "Tigreal", "Estes"),
            winRate = 48.5f, pickRate = 5.8f, banRate = 11.2f, tier = "C"
        ),
        HeroData(
            id = "kagura_alt",
            name = "Kagura (Alt)", nameCn = "神乐 (Alt)",
            role = "Mage", specialty = "Burst/Mobility",
            stats = HeroStats(2400, 480, 115, 95, 3.3f),
            skills = HeroSkills(
                passive = "Yin Yang Overturn — Umbrella enhances skills",
                skill1 = "Seimei Umbrella Open — Throws umbrella, AoE damage",
                skill2 = "Rasho Umbrella Flee — Teleport to umbrella, CC cleanse",
                ultimate = "Yin Yang Overturn — Umbrella pulls enemies, AoE burst"
            ),
            builds = listOf(
                Build("Core", listOf("Arcane Boots", "Lightning Truncheon", "Concentrated Energy", "Holy Crystal", "Divine Glaive", "Winter Truncheon"), "Standard burst mage"),
                Build("Damage", listOf("Arcane Boots", "Lightning Truncheon", "Holy Crystal", "Divine Glaive", "Fleeting Time", "Winter Truncheon"), "Full damage"),
                Build("Sustain", listOf("Arcane Boots", "Concentrated Energy", "Lightning Truncheon", "Winter Truncheon", "Immortality", "Athena's Shield"), "Survival mage")
            ),
            emblem = "Custom Mage", battleSpell = "Flicker",
            counters = listOf("Kaja", "Saber", "Eudora"),
            synergies = listOf("Angela", "Atlas", "Estes"),
            winRate = 52.1f, pickRate = 16.4f, banRate = 42.8f, tier = "S"
        )
    )

    fun getHeroById(id: String): HeroData? = heroes.find { it.id == id }

    fun getHeroesByRole(role: String): List<HeroData> = heroes.filter { it.role == role }

    fun getHeroesByTier(tier: String): List<HeroData> = heroes.filter { it.tier == tier }

    fun getTopHeroesByWinRate(count: Int): List<HeroData> = heroes.sortedByDescending { it.winRate }.take(count)

    fun getTopHeroesByPickRate(count: Int): List<HeroData> = heroes.sortedByDescending { it.pickRate }.take(count)

    fun getTopBanHeroes(count: Int): List<HeroData> = heroes.sortedByDescending { it.banRate }.take(count)

    fun getCounterHeroes(heroId: String): List<HeroData> {
        val hero = getHeroById(heroId) ?: return emptyList()
        return hero.counters.mapNotNull { getHeroById(it.lowercase().replace(" ", "")) }
    }

    fun getSynergyHeroes(heroId: String): List<HeroData> {
        val hero = getHeroById(heroId) ?: return emptyList()
        return hero.synergies.mapNotNull { getHeroById(it.lowercase().replace(" ", "")) }
    }
}
