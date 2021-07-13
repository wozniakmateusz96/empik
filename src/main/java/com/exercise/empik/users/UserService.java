package com.exercise.empik.users;

import com.exercise.empik.webclient.github.GithubClient;
import com.exercise.empik.webclient.github.GithubDto;
import com.exercise.empik.webclient.github.exceptions.NoFollowersException;
import com.exercise.empik.webclient.github.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.exercise.empik.webclient.github.GithubClient.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final GithubClient githubClient;

    @Transactional
    public UserDto getUser(String login) {

        GithubDto githubDto = githubClient.getUser(login);

        Optional<UserEntity> optionalUser = userRepository.findByLogin(login);

        optionalUser.ifPresent(userEntity -> userEntity.setRequestCount(userEntity.getRequestCount() + 1));
        if (!optionalUser.isPresent()) {
            userRepository.save(new UserEntity(login, 1));
        }

        if (githubDto.getErrorMessage().equals(USER_NOT_FOUND)) {
            throw new UserNotFoundException(login);
        }
        if (githubDto.getFollowers() == 0) {
            throw new NoFollowersException(login);
        }

        return UserDto.builder()
                .id(githubDto.getId())
                .login(githubDto.getLogin())
                .name(githubDto.getName())
                .type(githubDto.getType())
                .avatarUrl(githubDto.getAvatarUrl())
                .createdAt(githubDto.getCreatedAt())
                .calculations(calculations(githubDto.getFollowers(), githubDto.getPublicRepos()))
                .build();

    }


    private float calculations(int followersCount, int publicReposCount) {

        return (6F / (float) followersCount) * (2F + (float) publicReposCount);
    }

}
