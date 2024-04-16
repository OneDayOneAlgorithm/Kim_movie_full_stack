// SearchResults.js
import React from 'react';

const SearchResults = ({ searchMovies }) => {
  return (
    <div>
      <h3>검색 결과</h3>
      <div className="movie-container">
        {searchMovies.map(movie => (
          <div key={movie.id} className="movie">
            <img src={movie.posterPath} alt={movie.movieTitle} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default SearchResults;
