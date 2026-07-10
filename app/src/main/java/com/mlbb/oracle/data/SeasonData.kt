package com.mlbb.oracle.data

data class PatchNote(
    val version: String,
    val date: String,
    val summary: String,
    val heroChanges: List<String>,
    val itemChanges: List<String>,
    val systemChanges: List<String>
)

data class MetaHero(
    val heroId: String,
    val heroName: String,
    val role: String,
    val tier: String,
    val winRate: Float,
    val pickRate: Float,
    val banRate: Float,
    val reason: String
)

data class TierListEntry(
    val heroId: String,
    val heroName: String,
    val role: String,
    val tier: String,
    val explanation: String
)

object SeasonDatabase {

    val currentSeason = 32
    val currentYear = 2026

    val patchNotes = listOf(
        PatchNote(
            version = "32.1.1",
            date = "2026-06-28",
            summary = "Major balance patch — Assassins and Mages adjusted",
            heroChanges = listOf(
                "Fanny: Base HP reduced by 150, cable damage reduced by 8%",
                "Gusion: Skill 2 base damage increased by 50, AP ratio increased by 5%",
                "Ling: Ultimate cooldown increased by 5s, base damage reduced by 10%",
                "Kagura: Passive shield reduced by 20%, skill 1 damage increased by 8%",
                "Granger: Base attack increased by 5, ultimate execute threshold increased to 30%",
                "Chou: Skill 1 knockup duration reduced by 0.2s",
                "Yu Zhong: Passive healing increased by 10%, ultimate duration increased by 1s",
                "Esmeralda: Shield steal increased by 15%, base damage reduced by 5%",
                "Atlas: Ultimate pull range reduced by 5%, damage increased by 8%",
                "Wanwan: Ultimate requirement reduced from 5 to 4 weakness points"
            ),
            itemChanges = listOf(
                "Blade of Despair: Attack increased by 10, price increased by 150g",
                "Endless Battle: True damage reduced by 10%, HP increased by 200",
                "Athena's Shield: Magic defense increased by 10, passive shield increased by 15%",
                "Immortality: Revive HP increased from 15% to 18%"
            ),
            systemChanges = listOf(
                "Jungle camp spawn time reduced by 5s",
                "Lord spawn time changed from 18:00 to 17:00",
                "Gold per minute increased by 5%",
                "Vision range in bush reduced by 10%"
            )
        ),
        PatchNote(
            version = "32.1.0",
            date = "2026-06-15",
            summary = "Season 32 launch — New hero Arlott, meta reset",
            heroChanges = listOf(
                "All heroes: Base stats rebalanced for Season 32",
                "New hero Arlott released — Fighter/Assassin",
                "Balmond rework: New passive, enhanced skill effects",
                "Hilda: Skill 2 root duration increased by 0.3s"
            ),
            itemChanges = listOf(
                "New item: War Axe — Stacking attack and penetration",
                "Blade of the Heptaseas: Ambush damage increased by 20%",
                "Dominance Ice: Attack speed reduction increased from 15% to 20%"
            ),
            systemChanges = listOf(
                "Ranked mode reset for Season 32",
                "New season rewards: Epic skin for reaching Mythic",
                "Matchmaking algorithm updated for faster queue times",
                "Replay system improvements"
            )
        ),
        PatchNote(
            version = "31.9.8",
            date = "2026-06-01",
            summary = "Hotfix — Emergency adjustments to overperforming heroes",
            heroChanges = listOf(
                "Fanny: Cable distance reduced by 5%",
                "Gusion: Ultimate damage reduced by 12%",
                "Granger: Skill 1 damage reduced by 8%"
            ),
            itemChanges = listOf(
                "Blade of Despair: Critical damage bonus reduced from 40% to 35%"
            ),
            systemChanges = listOf(
                "Fixed visual bug in Lord pit",
                "Improved hit detection for projectile skills"
            )
        )
    )

    val metaHeroes = listOf(
        // Assassins
        MetaHero("fanny", "Fanny", "Assassin", "S", 52.3f, 12.8f, 45.2f, "Best assassin in the game with unmatched mobility. Dominates jungle and can solo carry."),
        MetaHero("gusion", "Gusion", "Assassin", "S", 51.8f, 14.2f, 38.7f, "High burst damage, snowball potential. Strong early-mid game power spike."),
        MetaHero("ling", "Ling", "Assassin", "S", 50.5f, 11.3f, 42.1f, "Best split-pusher in the game. High ban rate due to wall-hopping dominance."),
        MetaHero("lancelot", "Lancelot", "Assassin", "A", 51.2f, 10.5f, 35.8f, "Strong duelist with reset mechanics. Good pick into low-CC comps."),
        MetaHero("saber", "Saber", "Assassin", "A", 50.8f, 9.4f, 28.3f, "Best single-target burst. Counters squishy carries easily."),

        // Fighters
        MetaHero("chou", "Chou", "Fighter", "S", 52.8f, 15.6f, 48.3f, "Best fighter in the game. Kicks enemies into team, dominates lane."),
        MetaHero("yuzhong", "Yu Zhong", "Fighter", "S", 51.5f, 13.2f, 35.4f, "Strong sustain fighter with dragon form. Teamfight monster."),
        MetaHero("esmeralda", "Esmeralda", "Fighter", "A", 50.2f, 9.8f, 22.1f, "Shield steal makes her strong vs shield comps. Magic damage fighter."),
        MetaHero("paquito", "Paquito", "Fighter", "A", 51.0f, 10.2f, 25.6f, "High burst fighter with CC chain. Strong in skilled hands."),
        MetaHero("fredrinn", "Fredrinn", "Fighter", "A", 51.2f, 8.9f, 20.4f, "Tanky fighter with massive true damage ultimate. Good jungle pick."),

        // Mages
        MetaHero("kagura", "Kagura", "Mage", "S", 52.1f, 16.4f, 42.8f, "Best mage in the game. High mobility + burst + CC immunity."),
        MetaHero("pharsa", "Pharsa", "Mage", "A", 51.8f, 12.1f, 32.5f, "Best poke mage with global ultimate. Strong in teamfights."),
        MetaHero("lunox", "Lunox", "Mage", "A", 51.5f, 10.3f, 30.2f, "High burst with invincibility. Strong pick-off potential."),
        MetaHero("eudora", "Eudora", "Mage", "A", 50.5f, 11.8f, 28.9f, "Best burst mage. One-shots squishies with full combo."),

        // Marksmen
        MetaHero("granger", "Granger", "Marksman", "S", 53.2f, 18.5f, 45.8f, "Best marksman in the game. Burst damage + jungle potential."),
        MetaHero("wanwan", "Wanwan", "Marksman", "S", 52.5f, 13.8f, 40.2f, "Untargetable ultimate, high mobility. Strong in late game."),
        MetaHero("miya", "Miya", "Marksman", "A", 51.8f, 14.2f, 35.2f, "Best DPS marksman. Strong in teamfights with AoE basic attacks."),
        MetaHero("claude", "Claude", "Marksman", "A", 51.2f, 11.5f, 28.8f, "Best split-push marksman. Strong AoE ultimate."),

        // Tanks
        MetaHero("atlas", "Atlas", "Tank", "S", 52.8f, 16.2f, 42.5f, "Best tank in the game. 5-man ultimate wins teamfights."),
        MetaHero("khufra", "Khufra", "Tank", "A", 51.5f, 10.5f, 28.3f, "Best counter to dash heroes. Strong engage tool."),
        MetaHero("tigreal", "Tigreal", "Tank", "A", 50.5f, 12.8f, 25.4f, "Best beginner tank. Simple combo with massive AoE stun."),
        MetaHero("franco", "Franco", "Tank", "A", 50.2f, 8.8f, 22.6f, "Best pick-off tank. Hook wins games."),

        // Supports
        MetaHero("angela", "Angela", "Support", "S", 52.5f, 14.8f, 42.2f, "Best support in the game. Ultimate can save carries and enable dives."),
        MetaHero("estes", "Estes", "Support", "A", 51.8f, 11.2f, 35.8f, "Best healing support. Makes team nearly unkillable."),
        MetaHero("carmilla", "Carmilla", "Support", "A", 50.5f, 8.5f, 22.8f, "Strong CC support with magic damage. Good in teamfights."),
        MetaHero("mathilda", "Mathilda", "Support", "A", 50.8f, 9.2f, 24.5f, "Best roaming support. High mobility + healing.")
    )

    val tierList = listOf(
        // S Tier
        TierListEntry("fanny", "Fanny", "Assassin", "S", "Unmatched mobility and burst. Can solo carry games. Highest ban rate in the game."),
        TierListEntry("chou", "Chou", "Fighter", "S", "Best fighter with CC, mobility, and damage. Kicks enemies into death."),
        TierListEntry("kagura", "Kagura", "Mage", "S", "Best mage with high mobility, burst, and CC immunity."),
        TierListEntry("granger", "Granger", "Marksman", "S", "Best marksman with burst damage and jungle potential."),
        TierListEntry("atlas", "Atlas", "Tank", "S", "Best tank with 5-man ultimate that wins teamfights."),
        TierListEntry("angela", "Angela", "Support", "S", "Best support with global ultimate that saves carries."),
        TierListEntry("gusion", "Gusion", "Assassin", "S", "High burst damage, snowball potential."),
        TierListEntry("ling", "Ling", "Assassin", "S", "Best split-pusher with wall-hopping mobility."),
        TierListEntry("yuzhong", "Yu Zhong", "Fighter", "S", "Sustain fighter with dragon form teamfight dominance."),
        TierListEntry("wanwan", "Wanwan", "Marksman", "S", "Untargetable ultimate, high mobility."),

        // A Tier
        TierListEntry("miya", "Miya", "Marksman", "A", "Best DPS marksman with AoE basic attacks."),
        TierListEntry("lunox", "Lunox", "Mage", "A", "High burst with invincibility mechanic."),
        TierListEntry("eudora", "Eudora", "Mage", "A", "Best burst mage, one-shots squishies."),
        TierListEntry("pharsa", "Pharsa", "Mage", "A", "Best poke mage with global ultimate."),
        TierListEntry("esmeralda", "Esmeralda", "Fighter", "A", "Shield steal makes her strong vs shield comps."),
        TierListEntry("saber", "Saber", "Assassin", "A", "Best single-target burst assassin."),
        TierListEntry("lancelot", "Lancelot", "Assassin", "A", "Strong duelist with reset mechanics."),
        TierListEntry("paquito", "Paquito", "Fighter", "A", "High burst fighter with CC chain."),
        TierListEntry("fredrinn", "Fredrinn", "Fighter", "A", "Tanky fighter with true damage ultimate."),
        TierListEntry("claude", "Claude", "Marksman", "A", "Best split-push marksman with AoE ultimate."),
        TierListEntry("brody", "Brody", "Marksman", "A", "Strong burst marksman with execute."),
        TierListEntry("khufra", "Khufra", "Tank", "A", "Best counter to dash heroes."),
        TierListEntry("tigreal", "Tigreal", "Tank", "A", "Best beginner tank with AoE stun."),
        TierListEntry("franco", "Franco", "Tank", "A", "Best pick-off tank with hook."),
        TierListEntry("lolita", "Lolita", "Tank", "A", "Best support tank with projectile block."),
        TierListEntry("estes", "Estes", "Support", "A", "Best healing support."),
        TierListEntry("carmilla", "Carmilla", "Support", "A", "Strong CC support with magic damage."),
        TierListEntry("mathilda", "Mathilda", "Support", "A", "Best roaming support with mobility."),
        TierListEntry("thamuz", "Thamuz", "Fighter", "A", "Strong sustain fighter with fire aura."),
        TierListEntry("arlot", "Arlott", "Fighter", "A", "Chase fighter with CC chain."),
        TierListEntry("harith", "Harith", "Mage", "A", "Burst mage with dash resets."),
        TierListEntry("faramis", "Faramis", "Mage", "A", "Support mage with revive ultimate."),
        TierListEntry("valentina", "Valentina", "Mage", "A", "Burst mage that copies enemy ultimates."),
        TierListEntry("lylia", "Lylia", "Mage", "A", "Poke mage with bomb chain explosions."),
        TierListEntry("cyclops", "Cyclops", "Mage", "A", "Poke mage with auto-targeting orbs."),
        TierListEntry("diggie", "Diggie", "Support", "A", "Utility support with cleanse ultimate."),

        // B Tier
        TierListEntry("hayabusa", "Hayabusa", "Assassin", "B", "Good split-pusher but outclassed by other assassins."),
        TierListEntry("lapu", "Lapu-Lapu", "Fighter", "B", "Solid fighter but requires good positioning."),
        TierListEntry("terizla", "Terizla", "Fighter", "B", "Good teamfight fighter but slow and immobile."),
        TierListEntry("silvanna", "Silvanna", "Fighter", "B", "Magic damage fighter with strong ultimate."),
        TierListEntry("moskov", "Moskov", "Marksman", "B", "Strong DPS but immobile and easy to kill."),
        TierListEntry("lesley", "Lesley", "Marksman", "B", "Good burst but falls off late game."),
        TierListEntry("popol", "Popol and Kupa", "Marksman", "B", "Good push but weak in teamfights."),
        TierListEntry("irithel", "Irithel", "Marksman", "B", "Mobile marksman but weak early game."),
        TierListEntry("johnson", "Johnson", "Tank", "B", "Good engage but easily countered."),
        TierListEntry("nana", "Nana", "Support", "B", "Good poke but weak in teamfights."),
        TierListEntry("rafaela", "Rafaela", "Support", "B", "Good healer but low impact."),
        TierListEntry("floryn", "Floryn", "Support", "B", "Global heal but weak in fights."),
        TierListEntry("zhask", "Zhask", "Mage", "B", "Strong push but weak in fights."),
        TierListEntry("chang'e", "Chang'e", "Mage", "B", "Good poke but low burst."),
        TierListEntry("vale", "Vale", "Mage", "B", "Good burst but unreliable CC."),
        TierListEntry("aurora", "Aurora", "Mage", "B", "Strong CC but easily kited."),
        TierListEntry("odette", "Odette", "Mage", "B", "Strong AoE but easily interrupted."),
        TierListEntry("xavier", "Xavier", "Mage", "B", "Global ultimate but weak early game."),
        TierListEntry("celestrian", "Cecilion", "Mage", "B", "Scaling mage but weak early game."),

        // C Tier
        TierListEntry("hanabi", "Hanabi", "Marksman", "C", "Weak damage and easily killed."),
        TierListEntry("akai", "Akai", "Tank", "C", "Outclassed by other tanks."),
        TierListEntry("belerick", "Belerick", "Tank", "C", "Weak CC and easily killed."),
        TierListEntry("uranus", "Uranus", "Tank", "C", "Weak damage and CC."),
        TierListEntry("balmond", "Balmond", "Tank", "C", "Weak damage and outclassed."),
        TierListEntry("hilda", "Hilda", "Tank", "C", "Weak damage and CC."),
        TierListEntry("grock", "Grock", "Tank", "C", "Weak after nerfs."),
        TierListEntry("minotaur", "Minotaur", "Tank", "C", "Weak damage and CC."),
        TierListEntry(" gord", "Gord", "Mage", "C", "Weak mobility and damage.")
    )

    fun getMetaHeroesByRole(role: String): List<MetaHero> =
        metaHeroes.filter { it.role == role }

    fun getTierListByTier(tier: String): List<TierListEntry> =
        tierList.filter { it.tier == tier }

    fun getLatestPatch(): PatchNote = patchNotes.first()

    fun getOverallTierList(): Map<String, List<TierListEntry>> =
        tierList.groupBy { it.tier }

    fun getMetaSummary(): String {
        val sTier = tierList.filter { it.tier == "S" }.map { it.heroName }
        return "Season $currentSeason Meta: ${sTier.joinToString(", ")} dominate the current patch."
    }
}
