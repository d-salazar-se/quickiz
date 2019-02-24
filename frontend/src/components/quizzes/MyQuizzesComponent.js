import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
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
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      quizzes: [],
      takenQuizzes: [],
    };
  }

  componentDidMount() {
    this.getQuizzes();
  }

  getQuizzes() {
    axios
      .get(process.env.REACT_APP_API_URL + '/quizzes')
      .then(result => {
        this.setState({
          quizzes: result.data,
          isLoading: false,
        });
      })
      .catch(error => {
        this.setState({
          quizzes: [],
          isLoading: false,
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  render() {
    return (
      <div>
        <Typography variant="h4" gutterBottom>
          Mis Quizzes
        </Typography>

        <Grid container spacing={24}>
          {this.state.quizzes.map(quiz => {
            if (quiz.finishAt === null) {
              return (
                <Grid key={quiz.id} item xs={3}>
                  <Card>
                    <CardContent>
                      <Typography gutterBottom variant="h5" component="h2">
                        {quiz.quizStructure.name} disponible!
                      </Typography>
                    </CardContent>
                    <CardActions>
                      <Button component={Link} color="primary" to={'/my/quizzes/' + quiz.id}>
                        Tomar Quiz
                      </Button>
                    </CardActions>
                  </Card>
                </Grid>
              );
            }
            return ("");
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
              {this.state.quizzes.map(quiz => {
                if (quiz.finishAt !== null) {
                  return (
                    <TableRow key={quiz.id}>
                      <TableCell component="th" scope="quiz">
                        {quiz.quizStructure.name}
                      </TableCell>
                      <TableCell>{quiz.score + 1}</TableCell>
                      <TableCell>
                        <Button color="default" component={Link} to={'/my/quizzes/' + quiz.id + '/result'}>
                          Ver mi respuesta
                        </Button>
                      </TableCell>
                    </TableRow>
                  );
                }
                return ("");
              })}
            </TableBody>
          </Table>
        </Paper>
      </div>
    );
  }
}

export default withSnackbar(MyQuizzesComponent);
