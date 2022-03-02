import React, { Component } from 'react'
import CourseDataService from '../service/MovieDataService.js';
import { Button, ButtonGroup } from 'reactstrap';
import { Link } from 'react-router-dom';

const USER = ''; //Change to be dynamic to get favorites movie by user

class ListMoviesComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            movies: [],
            message: null
        }
        this.refreshCourses = this.refreshCourses.bind(this)
    }

    remove(id) {
        CourseDataService.delete(id)
          .then(
            response => {
                this.refreshCourses();
            }
          )
    }

    componentDidMount() {
        this.refreshCourses();
    }

    refreshCourses() {
        CourseDataService.retrieveAllMovies(USER)
            .then(
                response => {
                    this.setState({ movies: response.data })
                }
            )
    }

    render() {
        return (
            <div className="container">
                <h3>All Movies</h3>
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <Link to={"/movies/new"}>
                                        <Button color="success">Add</Button>
                                    </Link>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Name</th>
                                <th>Genre</th>
                                <th>Year</th>
                                <th>Operations</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.movies.map(
                                    movie =>
                                        <tr key={movie.id}>
                                            <td>{movie.id}</td>
                                            <td>{movie.name}</td>
                                            <td>{movie.genre}</td>
                                            <td>{movie.year}</td>
                                            <td>
                                                <ButtonGroup>
                                                    <Button size="sm" color="primary" tag={Link} to={"/movies/" + movie.id}>Edit</Button>
                                                    <Button size="sm" color="danger" onClick={() => this.remove(movie.id)}>Delete</Button>
                                                </ButtonGroup>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default ListMoviesComponent
