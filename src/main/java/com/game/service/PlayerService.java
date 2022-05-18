package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Integer getCount(Player player) { return Integer.valueOf((int)playerRepository.count()); }

    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    public Player get(Long id) {
        return playerRepository.findById(id).get();
    }

    public Player createAndUpdate(Player player) {
        return playerRepository.save(player);
    }

    public boolean isEmptyBody(Player player) {
        if (player.getName() == null && player.getTitle() == null && player.getRace() == null
                && player.getProfession() == null && player.getBirthday() == null
                && player.isBanned() == null && player.getExperience() == null) {
            return true;
        }
        return false;
    }
    public Boolean isIdNotNull(Long id) {
        if (id != null) {
            return true;
        }
        return false;
    }
    public Boolean isIdValid(Long id) {
        if (id > 0L) {
            if (id % 1 == 0L) {
                return true;
            }
        }
        /*if (!String.valueOf(id).matches("^-?\\d+$")) {
            System.out.println("id is not digit");
            return false;
        }*/
        return false;
    }

    public Boolean exists(Long id) {
        return playerRepository.existsById(id);
    }

    public Boolean isNameNotNull(Player player) {
        if (player.getName() != null) {
            return true;
        }
        return false;
    }
    public Boolean isNameValid(Player player) {
        if ((!(player.getName().equals("")))) {
            if (player.getName() instanceof String) {
                if ((player.getName().length() > 0) && (player.getName().length() <= 12)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isTitleNotNull(Player player) {
        if (player.getTitle() != null) {
            return true;
        }
        return false;
    }
    public Boolean isTitleValid(Player player) {
        if (player.getTitle() instanceof String) {
            if ((player.getTitle().length() > 0) && (player.getTitle().length() <= 30)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isRaceNotNull(Player player) {
        if (player.getRace() != null) {
            return true;
        }
        return false;
    }
    public Boolean isRaceValid(Player player) {
        if (player.getRace() instanceof Enum) {
            return true;
        }
        return false;
    }

    public Boolean isProfessionNotNull(Player player) {
        if (player.getProfession() != null) {
            return true;
        }
        return false;
    }
    public Boolean isProfessionValid(Player player) {
        if (player.getProfession() instanceof Enum) {
            return true;
        }
        return false;
    }

    public Boolean isExperienceNotNull(Player player) {
        if (player.getExperience() != null) {
            return true;
        }
        return false;
    }
    public Boolean isExperienceValid(Player player) {
        if (player.getExperience() instanceof Integer) {
            if ((player.getExperience() >= 0) && (player.getExperience() <= 10_000_000)) {
                return true;
            }
        }
        return false;
    }

    public Boolean isBirthdayNotNull(Player player) {
        if (player.getBirthday() != null) {
            return true;
        }
        return false;
    }
    public Boolean isBirthdayValid(Player player) {
        if (player.getBirthday().getTime() >= 0) {
            return true;
        }
        return false;
    }

    public Boolean isBannedNotNull(Player player) {
        if (player.isBanned() != null) {
            return true;
        }
        return false;
    }
    public Boolean isBannedValid(Player player) {
        if (player.isBanned() instanceof Boolean) {
            return true;
        }
        return false;
    }

    public Boolean isDataParamsNotNull(Player player) {
        if (isNameNotNull(player) && isTitleNotNull(player) && isRaceNotNull(player)
                && isProfessionNotNull(player) && isBirthdayNotNull(player)
                && isBannedNotNull(player) && isExperienceNotNull(player)) {
            return true;
        }
        return false;
    }
    public Boolean isDataParamsValid(Player player) {
        if (isNameValid(player) && isTitleValid(player) && isRaceValid(player)
                && isProfessionValid(player) && isBirthdayValid(player)
                && isBannedValid(player) && isExperienceValid(player)) {
            return true;
        }
        return false;
    }
}