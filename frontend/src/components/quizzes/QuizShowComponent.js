import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';

import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

class QuizShowComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quiz: {
        id: this.props.match.params.id,
        name: '',
        duration: 0,
        slackTime: 0,
        questionsStructures: []
      },
    };
  }

  componentDidMount() {
    this.getQuiz();
  }

  getQuiz() {
    axios
      .get(
        process.env.REACT_APP_API_URL + '/quizstructures/' + this.state.quiz.id
      )
      .then(result => {
          let quiz = result.data;
          // remover camelCase :S
          quiz.slacktime = quiz.slackTime;
          delete quiz.slackTime;
          this.setState({ quiz: quiz });
        }
      )
      .catch(error =>
        this.setState({ quiz: [] })
      );
  }

  render() {
    return (
      <div>
        <div className="panelcrear">
          <div className="formcrear">
            <Typography variant="h4" gutterBottom>
              Nombre del Quiz: {this.state.quiz.name}
            </Typography>
            <Typography style={{ fontSize: 20 }} variant="h4" gutterBottom>
              Duración: {this.state.quiz.duration} min.
            </Typography>
            <Typography style={{ fontSize: 20 }} variant="h4" gutterBottom>
              Tiempo de holgura: {this.state.quiz.slacktime} min.
            </Typography>

            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Pozo</TableCell>
                  <TableCell align="right">Puntaje</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                { this.state.quiz.questionsStructures.map(question => {
                    return (
                      <TableRow key={question.id}>
                        <TableCell component="th" scope="row">
                          { question.pool.name }
                        </TableCell>
                        <TableCell align="right">
                          { question.score }
                        </TableCell>
                      </TableRow>
                    );
                  })
                }
              </TableBody>
            </Table>

            <Button component={Link} to="/quizzes">
              Volver
            </Button>
          </div>
        </div>
      </div>
    );
  }
}

export default QuizShowComponent;
