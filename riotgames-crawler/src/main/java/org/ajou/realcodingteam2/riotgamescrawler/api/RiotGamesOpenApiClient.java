package org.ajou.realcodingteam2.riotgamescrawler.api;

import io.swagger.models.HttpMethod;
import org.ajou.realcodingteam2.riotgamescrawler.domain.Game;
import org.ajou.realcodingteam2.riotgamescrawler.domain.League;
import org.ajou.realcodingteam2.riotgamescrawler.domain.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotGamesOpenApiClient {

    @Autowired
    private RestTemplate restTemplate;

    private static final String SUMMONERINFO_REQUEST = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/{summonerName}?api_key=RGAPI-27cfbedd-ba4b-4b0f-8eb9-1c42338d3b37";

    private static final String LEAGUEINFO_REQUEST = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/{summonerId}?api_key=RGAPI-27cfbedd-ba4b-4b0f-8eb9-1c42338d3b37";

    private static final String GAMEINFO_REQUEST = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account/{accountId}?endIndex=99&beginIndex=0&api_key=RGAPI-27cfbedd-ba4b-4b0f-8eb9-1c42338d3b37";


    public Summoner getSummonerInfo(String summonerName){
        Summoner summoner = restTemplate.getForObject(SUMMONERINFO_REQUEST, Summoner.class, summonerName);
        return summoner;
    }

    public League getLeagueInfo(String summonerId){
        League[] leagues = restTemplate.getForObject(LEAGUEINFO_REQUEST, League[].class, summonerId);
        for(League league : leagues) {
            if (league.getQueueType().equals("RANKED_SOLO_5x5")) return league;
        }
        return null;
    }
    public Game getGameInfo(String accountId){
        Game game = restTemplate.getForObject(GAMEINFO_REQUEST, Game.class, accountId);
        return game;
    }
}
