import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [movies, setMovies] = useState([]);
  const [recommendMovies, setRecommendMovies] = useState([]);
  const [searchMovies, setSearchRecommendMovies] = useState([]);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showSignupModal, setShowSignupModal] = useState(false);
  const [showProfileModal, setShowProfileModal] = useState(false);
  const [showMovieDetailModal, setShowMovieDetailModal] = useState(false); // 영화 상세 정보 모달
  const [selectedMovie, setSelectedMovie] = useState(null); // 선택된 영화 정보
  const [searchInput, setSearchInput] = useState('');
  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');
  const [profile, setProfile] = useState([]);
  const [loginError, setLoginError] = useState('');
  const [signupError, setSignupError] = useState('');
  const [likeStatus, setLikeStatus] = useState(false); // 찜 상태
  const [likeStatus2, setLikeStatus2] = useState(false); // 좋아요 상태
  const [signupEmail, setSignupEmail] = useState(''); // 회원가입 이메일
  const [signupPassword, setSignupPassword] = useState(''); // 회원가입 비밀번호
  const [signupUsername, setSignupUsername] = useState(''); // 회원가입 닉네임
  const [favoriteMovies, setFavoriteMovies] = useState([]); // 찜한 영화 목록

  useEffect(() => {
    const storedProfile = localStorage.getItem('profile');
    if (storedProfile) {
      setProfile(JSON.parse(storedProfile));
    }
    fetchMovies();
  }, []);

  useEffect(() => {
    if (profile?.memberId) {
      fetchRecommendMovies();
      fetchFavoriteMovies();
    }
  }, [profile]);

  useEffect(() => {
    if (selectedMovie && profile?.memberId) {
      fetchLikeStatus();
      fetchFavoriteMovies();
      fetchFavoriteMovies();
    }
  }, [selectedMovie]);

  const fetchMovies = async () => {
    try {
      const response = await fetch('http://localhost:8080/movies');
      const data = await response.json();
      setMovies(data);
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  };
  const fetchRecommendMovies = async () => {
    try {
      if (profile?.memberId) {
        const response = await fetch('http://localhost:8080/movies/recommend/' + profile.memberId);
        const data = await response.json();
        if (Array.isArray(data)) {
          setRecommendMovies(data);
        } else {
          setRecommendMovies([]);
        }
      } else {
        setRecommendMovies([]);
      }
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  };
  

  const fetchSearchMovies = async (search) => {
    try {
      const response = await fetch('http://localhost:8080/movies/name/' + search);
      const data = await response.json();
      if (Array.isArray(data)) {
        setSearchRecommendMovies(data);
      } else {
        setSearchRecommendMovies([]);
      }
    } catch (error) {
      console.error('Error fetching movies:', error);
    }
  };

  const handleLoginButtonClick = () => {
    setShowLoginModal(true);
  };

  const handleSignupButtonClick = () => {
    setShowSignupModal(true);
  };

  const handleProfileButtonClick = () => {
    setShowProfileModal(true);
    fetchFavoriteMovies();
  };

    // handleSignup 함수와 같은 위치에 상태 변경 함수들을 추가해줍니다.
    const handleSignupEmailChange = (event) => {
      setSignupEmail(event.target.value);
    };
  
    const handleSignupPasswordChange = (event) => {
      setSignupPassword(event.target.value);
    };
  
    const handleSignupUsernameChange = (event) => {
      setSignupUsername(event.target.value);
    };

  const closeModal = () => {
    setShowLoginModal(false);
    setShowSignupModal(false);
    setShowProfileModal(false);
    setShowMovieDetailModal(false); // 영화 상세 정보 모달 닫기
  };

  const handleSearchInputChange = (event) => {
    const inputValue = event.target.value;
    setSearchInput(inputValue);
    fetchSearchMovies(inputValue);
  };

const handleSignup = async () => {
  try {
    const response = await fetch('http://localhost:8080/members', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: signupEmail,
        password: signupPassword,
        username: signupUsername,
      }),
    });
    const data = await response.json();
    // 회원가입 성공 여부에 따라 처리
    if (response.ok) {
      // 회원가입 성공 시 로그인 모달 닫기
      setShowSignupModal(false);
      // 로그인 모달 열기 또는 다른 처리 수행
      setShowLoginModal(true); // 예시로 로그인 모달 열기
      setSignupError(''); // API에서 반환하는 에러 메시지 사용
    } else {
      // 회원가입 실패 시 에러 메시지 표시
      setSignupError('중복되는 이메일이 있습니다.'); // API에서 반환하는 에러 메시지 사용
    }
  } catch (error) {
    console.error('Error signing up:', error);
  }
};

  const handleLogin = async () => {
    try {
      const response = await fetch('http://localhost:8080/members/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: loginEmail,
          password: loginPassword,
        }),
      });
      const data = await response.json();
      if (data.memberId) {
        localStorage.setItem('profile', JSON.stringify(data));
        setProfile(data);
        setLoginError('');
        setShowLoginModal(false);
      } else {
        setLoginError('ID와 PW를 다시 확인해 주세요.');
      }
    } catch (error) {
      console.error('Error logging in:', error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('profile');
    setProfile([]);
  };

  const handleProfile = () => {
    setShowProfileModal(true);
  };

  // 영화 포스터 클릭 시 모달창 열기
  const handleMovieClick = (movie) => {
    setSelectedMovie(movie);
    setShowMovieDetailModal(true);
  };

  // 찜 버튼 클릭 시
  const handleLikeButtonClick = async () => {
    try {
      // 좋아요를 누른 영화의 좋아요 상태를 토글
      const response = await fetch(`http://localhost:8080/movies/dibs/${profile.memberId}/${selectedMovie.movieId}`, {
        method: 'GET',
      });
      if (response.ok) {
        // 좋아요 상태를 반영하여 해당 영화 정보 다시 가져오기
        const dibsResponse = await fetch(`http://localhost:8080/movies/dibs/true/${profile.memberId}/${selectedMovie.movieId}`);
        const data = await dibsResponse.json();
        setLikeStatus(data); // 찜 상태 업데이트
      }
    } catch (error) {
      console.error('Error toggling like:', error);
    }
  };


  // 좋아요 버튼 클릭 시
  const handleLike2ButtonClick = async () => {
    try {
      // 좋아요를 누른 영화의 좋아요 상태를 토글
      const response = await fetch(`http://localhost:8080/movies/like/${profile.memberId}/${selectedMovie.movieId}`, {
        method: 'GET',
      });
      if (response.ok) {
        // 좋아요 상태를 반영하여 해당 영화 정보 다시 가져오기
        const dibsResponse = await fetch(`http://localhost:8080/movies/like/true/${profile.memberId}/${selectedMovie.movieId}`);
        const data = await dibsResponse.json();
        setLikeStatus2(data); // 좋아요 상태 업데이트
      }
    } catch (error) {
      console.error('Error toggling like:', error);
    }
  };

  const handleWithdrawal = async () => {
    try {
      const response = await fetch(`http://localhost:8080/members/${profile.memberId}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        // 회원 탈퇴 성공 시 로컬 스토리지와 프로필 정보 초기화
        localStorage.removeItem('profile');
        setProfile([]);
        setShowProfileModal(false); // 모달 닫기
      } else {
        console.error('Withdrawal failed:', response.statusText);
      }
    } catch (error) {
      console.error('Error withdrawing:', error);
    }
  };


  const fetchLikeStatus = async () => {
    try {
      // 첫 번째 API 요청
      const response1 = await fetch(`http://localhost:8080/movies/dibs/true/${profile.memberId}/${selectedMovie.movieId}`, {
        method: 'GET',
      });
      if (response1.ok) {
        const data1 = await response1.json();
        setLikeStatus(data1);
      } else {
        // 첫 번째 API 요청 실패 시, likeStatus 상태를 false로 설정
        setLikeStatus(false);
        // 사용자에게 오류 메시지 표시
        console.error('Error fetching like status:', response1.statusText);
      }
    } catch (error) {
      console.error('Error fetching like status:', error);
    }
    
    try {
      // 두 번째 API 요청
      const response2 = await fetch(`http://localhost:8080/movies/like/true/${profile.memberId}/${selectedMovie.movieId}`, {
        method: 'GET',
      });
      if (response2.ok) {
        const data2 = await response2.json();
        setLikeStatus2(data2);
      } else {
        // 두 번째 API 요청 실패 시, likeStatus2 상태를 false로 설정
        setLikeStatus2(false);
        // 사용자에게 오류 메시지 표시
        console.error('Error fetching like status:', response2.statusText);
      }
    } catch (error) {
      console.error('Error fetching like status:', error);
    }
    
  };

  const fetchFavoriteMovies = async () => {
    try {
      const response = await fetch(`http://localhost:8080/movies/dibs/${profile.memberId}`);
      if (response.ok) {
        const data = await response.json();
        setFavoriteMovies(data.map(movie => movie.movieTitle));
      } else {
        console.error('Error fetching favorite movies:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching favorite movies:', error);
    }
  };

  return (
    <>
      {!profile?.username && (
        <>
          <button className="signup-button" onClick={handleSignupButtonClick}>회원가입</button>
          <button className="login-button" onClick={handleLoginButtonClick}>로그인</button>
        </>
      )}
      {profile?.username && (
        <>
          <button className="signup-button" onClick={handleProfileButtonClick}>프로필</button>
          <button className="login-button" onClick={handleLogout}>로그아웃</button>
        </>
      )}
      {/* 회원가입 모달 */}
      {showSignupModal && (
  <div className="modal" onClick={closeModal}>
    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
      <span className="close" onClick={closeModal}>×</span>
      <input type="text" placeholder="이메일" className="signup-input" value={signupEmail} onChange={handleSignupEmailChange} />
      <input type="password" placeholder="비밀번호" className="signup-input" value={signupPassword} onChange={handleSignupPasswordChange} />
      <input type="text" placeholder="닉네임" className="signup-input" value={signupUsername} onChange={handleSignupUsernameChange} />
      <button onClick={handleSignup}>가입하기</button>
      {signupError && <p className="error-message">{signupError}</p>}
    </div>
  </div>
)}

      {/* 로그인 모달 */}
      {showLoginModal && (
        <div className="modal" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <span className="close" onClick={closeModal}>×</span>
            <input type="text" placeholder="이메일" className="login-input" value={loginEmail} onChange={(e) => setLoginEmail(e.target.value)} />
            <input type="password" placeholder="비밀번호" className="login-input" value={loginPassword} onChange={(e) => setLoginPassword(e.target.value)} />
            <button onClick={handleLogin}>로그인</button>
            {loginError && <p className="error-message">{loginError}</p>}
          </div>
        </div>
      )}
      {/* 프로필 모달 */}
      {showProfileModal && (
        <div className="modal" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <span className="close" onClick={closeModal}>×</span>
            <h2>프로필</h2>
            <p>닉네임: {profile.username}</p>
            <p>이메일: {profile.email}</p>
            <p>가입날짜: {profile.dateJoined}</p>
            <button onClick={handleWithdrawal}>회원 탈퇴</button>
            <h3>찜한 영화 목록</h3>
            <div className="favorite-movies">
              {favoriteMovies.length > 0 ? (
                <ul>
                  {favoriteMovies.map((movie, index) => (
                    <li key={index}>{movie}</li>
                  ))}
                </ul>
              ) : (
                <p>찜한 영화가 없습니다.</p>
              )}
            </div>
          </div>
        </div>
      )}
      {/* 영화 상세 정보 모달 */}
      {showMovieDetailModal && selectedMovie && (
        <div className="modal" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <span className="close" onClick={closeModal}>×</span>
            <h2>{selectedMovie.movieTitle}</h2>
            <p>개봉일: {selectedMovie.releaseDate}</p>
            <p>줄거리: {selectedMovie.overview}</p>
            <button onClick={handleLike2ButtonClick} style={{ backgroundColor: likeStatus2 ? 'red' : 'lightgray' }}>좋아요</button> {/* 좋아요 버튼 */}
            <button onClick={handleLikeButtonClick} style={{ backgroundColor: likeStatus ? 'red' : 'lightgray' }}>찜</button> {/* 찜 버튼 */}
          </div>
        </div>
      )}

      <div className="search-container">
        <input type="text" placeholder="영화를 검색하세요" className="search-input" value={searchInput} onChange={handleSearchInputChange} />
        {/* 검색 버튼 제거 */}
      </div>

      {/* 검색어가 입력되면 검색 결과를 표시 */}
      {searchInput.length > 0 && (
        <>
          <h3>검색 결과</h3>
          <div className="movie-container">
            {searchMovies.map(movie => (
              <div className="movie" key={movie.id} onClick={() => handleMovieClick(movie)}>
                <img src={movie.posterPath} alt={movie.movieTitle} />
              </div>
            ))}
          </div>
        </>
      )}

      {/* 검색어가 없을 때만 최근 인기 영화와 김형진님에게 추천하는 영화 섹션을 보여줌 */}
      {searchInput.length === 0 && (
        <>
          <h3>최근 인기 영화</h3>
          <div className="movie-container">
            {movies.map(movie => (
              <div className="movie" key={movie.id} onClick={() => handleMovieClick(movie)}>
                <img src={movie.posterPath} alt={movie.movieTitle} />
              </div>
            ))}
          </div>
          {profile?.username ? (
            <>
              <h3>{profile.username}님에게 추천하는 영화</h3>
              <div className="movie-container">
                {recommendMovies.map(movie => (
                  <div className="movie" key={movie.id} onClick={() => handleMovieClick(movie)}>
                    <img src={movie.posterPath} alt={movie.movieTitle} />
                  </div>
                ))}
              </div>
            </>
          ) : (
            <p>로그인을 하고 맞춤형 영화를 추천받아보세요.</p>
          )}
        </>
      )}
    </>
  );
}

export default App;
