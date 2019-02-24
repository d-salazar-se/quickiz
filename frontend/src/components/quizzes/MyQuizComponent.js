import React, { Component } from 'react';

import { Link } from 'react-router-dom';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Grid from '@material-ui/core/Grid';

class MyQuizzesComponent extends Component {
  state = {
    pendingQuizzes: [
      {
        id: 5,
        name: 'Quiz #5',
        grade: null,
      },
      {
        id: 6,
        name: 'Quiz #6',
        grade: null,
      },
    ],
    takenQuizzes: [
      {
        id: 1,
        name: 'Quiz #1',
        grade: 7,
      },
      {
        id: 2,
        name: 'Quiz #2',
        grade: 4,
      },
      {
        id: 3,
        name: 'Quiz #3',
        grade: 7,
      },
      {
        id: 4,
        name: 'Quiz #4',
        grade: 1,
      },
    ],
  };

  render() {
    return (
      <div>
        <Typography variant="h4" gutterBottom>
          Mis Quizzes
        </Typography>

        <Grid container spacing={24}>
          {this.state.pendingQuizzes.map(quiz => {
            return (
              <Grid key={quiz.id} item xs={3}>
                <Card>
                  <CardContent>
                    <Typography gutterBottom variant="h5" component="h2">
                      {quiz.name} disponible!
                    </Typography>
                    <Typography component="p">
                      El quiz cierra en {quiz.time} mins
                    </Typography>
                  </CardContent>
                  <CardActions>
                    <Button
                      component={Link}
                      color="primary"
                      to={'/my/quizzes/' + quiz.id}
                    >
                      Tomar Quiz
                    </Button>
                  </CardActions>
                </Card>
              </Grid>
            );
          })}
        </Grid>

        <br />
        <br />
        <br />

        <Paper>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nombre del Quiz</TableCell>
                <TableCell>Nota</TableCell>
                <TableCell />
              </TableRow>
            </TableHead>
            <TableBody>
              {this.state.takenQuizzes.map(quiz => {
                return (
                  <TableRow key={quiz.id}>
                    <TableCell component="th" scope="quiz">
                      {quiz.name}
                    </TableCell>
                    <TableCell>{quiz.grade}</TableCell>
                    <TableCell>
                      <Button
                        color="default"
                        component={Link}
                        to={'/my/quizzes/' + quiz.id}
                      >
                        Ver mi respuesta
                      </Button>
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </Paper>
      </div>
    );
  }
}

export default MyQuizzesComponent;
