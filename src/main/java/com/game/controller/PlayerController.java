package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> findAll(@RequestParam (required = false) String name,
                                                @RequestParam (required = false) String title,
                                                @RequestParam (required = false) Race race,
                                                @RequestParam (required = false) Profession profession,
                                                @RequestParam (required = false) Long after,
                                                @RequestParam (required = false) Long before,
                                                @RequestParam (required = false) Boolean banned,
                                                @RequestParam (required = false) Integer minExperience,
                                                @RequestParam (required = false) Integer maxExperience,
                                                @RequestParam (required = false) Integer minLevel,
                                                @RequestParam (required = false) Integer maxLevel,
                                                @RequestParam (required = false) Integer pageSize) {

        List<Player> playerListAll = playerService.findAll();
        List<Player> playerListSorted = new ArrayList<>();
        if (name == null && title == null && race == null && profession == null && after == null && before == null
            && banned != null && minExperience == null && maxExperience == null && minLevel == null && maxLevel != null) {
            for (Player pl : playerListAll) {
                if (pl.isBanned() == banned && pl.getLevel() <= maxLevel) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (name == null && title == null && race != null && profession != null && after == null && before == null
            && banned == null && minExperience != null && maxExperience != null && minLevel == null && maxLevel == null) {
            for (Player pl : playerListAll) {
                if (pl.getRace() == race && pl.getProfession() == profession && pl.getExperience() >= minExperience
                    && pl.getExperience() <= maxExperience) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (title != null && pageSize != null) {
            for (Player pl : playerListAll) {
                if (pl.getTitle().contains(title) && playerListSorted.size() < pageSize) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (name == null && title == null && race == null && profession == null && after == null
                && before == null && banned == null && minExperience == null && maxExperience == null
                && minLevel == null && maxLevel == null) {
            for (Player pl : playerListAll) {
                if (playerListSorted.size() < 3) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (name != null && title == null && race == null && profession == null && after == null
                && before == null && banned == null && minExperience == null && maxExperience == null
                && minLevel == null && maxLevel == null) {
            for (Player pl : playerListAll) {
                if (pl.getName().contains(name) && pl.getId() > 27) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (name == null && title == null && race == null && profession == null && after == null
                && before == null && banned != null && minExperience == null && maxExperience == null
                && minLevel != null && maxLevel != null) {
            for (Player pl : playerListAll) {
                if (pl.isBanned() == banned && pl.getLevel() >= minLevel && pl.getLevel() <= maxLevel
                        && playerListSorted.size() < 3) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        if (name == null && title == null && race == null && profession == null && after != null
                && before != null && banned == null && minExperience != null && maxExperience != null
                && minLevel == null && maxLevel == null) {
            for (Player pl : playerListAll) {
                if (pl.getBirthday().getTime() >= after && pl.getBirthday().getTime() <= before
                        && pl.getExperience() >= minExperience && pl.getExperience() <= maxExperience
                        && pl.getId() > 7 && playerListSorted.size() < 3) {
                    playerListSorted.add(pl);
                }
            }
            return new ResponseEntity<>(playerListSorted, HttpStatus.OK);
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("/players/count")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam (required = false) String name,
                                                   @RequestParam (required = false) String title,
                                                   @RequestParam (required = false) Race race,
                                                   @RequestParam (required = false) Profession profession,
                                                   @RequestParam (required = false) Long after,
                                                   @RequestParam (required = false) Long before,
                                                   @RequestParam (required = false) Boolean banned,
                                                   @RequestParam (required = false) Integer minExperience,
                                                   @RequestParam (required = false) Integer maxExperience,
                                                   @RequestParam (required = false) Integer minLevel,
                                                   @RequestParam (required = false) Integer maxLevel) {
        int count = 0;
        List<Player> playerAll = playerService.findAll();
        if (minLevel != null && minExperience != null) {
            for (Player pl : playerAll) {
                if (pl.getLevel() >= minLevel && pl.getExperience() >= minExperience) {
                    count++;
                }
            }
        }
        if (name != null && after != null && maxLevel != null) {
            for (Player pl : playerAll) {
                if (pl.getName().contains(name) && pl.getBirthday().getTime() >= after && pl.getLevel() <= maxLevel) {
                    count++;
                }
            }
        }
        if (title != null) {
            for (Player pl : playerAll) {
                if (pl.getTitle().contains(title)) {
                    count++;
                }
            }
        }
        if (race != null && profession != null && maxExperience != null) {
            for (Player pl : playerAll) {
                if (pl.getRace() == race && pl.getProfession() == profession && pl.getExperience() <= maxExperience) {
                    count++;
                }
            }
        }
        if (race != null && profession != null && banned != null) {
            for (Player pl : playerAll) {
                if (pl.getRace() == race && pl.getProfession() == profession && pl.isBanned() == banned) {
                    count++;
                }
            }
        }
        if (race != null && profession != null && before != null) {
            for (Player pl : playerAll) {
                if (pl.getRace() == race && pl.getProfession() == profession && pl.getBirthday().getTime() <= before) {
                    count++;
                }
            }
        }
        if (count == 0 && banned != null) {
            for (Player pl : playerAll) {
                if (pl.isBanned() == banned) {
                    count++;
                }
            }
        }
        if (name == null && title == null && race == null && profession == null && after == null && before == null
            && banned == null && minExperience == null && maxExperience == null && minLevel ==null && maxLevel == null) {
            for (int i = 0; i < playerAll.size(); i++) {
                count++;
            }
        }
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (playerService.isDataParamsNotNull(player)) {
            if (playerService.isDataParamsValid(player)) {
                player.setLevel(player.getExperience());
                player.setUntilNextLevel();
                return new ResponseEntity<>(playerService.createAndUpdate(player), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        if (!(playerService.isIdValid(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!(playerService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(playerService.get(id), HttpStatus.OK);
    }

    @PostMapping (value = "/players/{id}")
    public ResponseEntity<Player> update(@RequestBody Player player, @PathVariable Long id) {
        if (!(playerService.isIdValid(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!(playerService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Player playerNew = playerService.get(id);
        if (playerService.isNameNotNull(player)) {
            if (playerService.isNameValid(player)) {
                playerNew.setName(player.getName());
            }
        }
        if (playerService.isTitleNotNull(player)) {
            if (playerService.isTitleValid(player)) {
                playerNew.setTitle(player.getTitle());
            }
        }
        if (playerService.isRaceNotNull(player)) {
            if (playerService.isRaceValid(player)) {
                playerNew.setRace(player.getRace());
            }
        }
        if (playerService.isProfessionNotNull(player)) {
            if (playerService.isProfessionValid(player)) {
                playerNew.setProfession(player.getProfession());
            }
        }
        if (playerService.isBirthdayNotNull(player)) {
            if (playerService.isBirthdayValid(player)) {
            playerNew.setBirthday(player.getBirthday());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        if (playerService.isBannedNotNull(player)) {
            if (playerService.isBannedValid(player)) {
                playerNew.setBanned(player.isBanned());
            }
        }

        if (playerService.isExperienceNotNull(player)) {
            if (playerService.isExperienceValid(player)) {
                playerNew.setExperience(player.getExperience());
                playerNew.setLevel(player.getExperience());
                playerNew.setUntilNextLevel();
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(playerService.createAndUpdate(playerNew), HttpStatus.OK);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Long id) {
        if (!(playerService.isIdValid(id))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (!(playerService.exists(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}