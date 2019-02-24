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

class QuizIndexComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quizzes: [],
    };

    this.delete = this.delete.bind(this);
    this.generateQuiz = this.generateQuiz.bind(this);
  }

  componentDidMount() {
    this.getQuizzes();
  }

  getQuizzes() {
    axios
      .get(process.env.REACT_APP_API_URL + '/quizstructures/')
      .then(result =>
        this.setState({ quizzes: result.data })
      )
      .catch(error =>
        this.setState({ quizzes: [] })
      );
  }

  delete(event) {
    if (window.confirm('¿Esta seguro que desea eliminar el quiz?')) {
      const id = event.target.id.value;

      axios
        .delete(process.env.REACT_APP_API_URL + '/quizstructures/' + id)
        .then(result => {
          this.getQuizzes();

          console.log('quiz eliminado');
          // window.M.toast({html: 'Producto Eliminado'});
        })
        .catch(error => {
          console.error(error);
        });
    }

    event.preventDefault();
  }

  generateQuiz(event){

      const id = event.target.id.value;

      axios
      .post(process.env.REACT_APP_API_URL + '/quizzes/'+id+'/generate')
      .then(result => {
        // this.setState({ isLoading: false });
       
        this.props.history.push('/my/quizzes');
        this.props.enqueueSnackbar('Quiz Generado!', { variant: 'success' });
      })
      .catch(error => {
        // this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al generar el quiz!', {
          variant: 'error',
        });
      });
    

    event.preventDefault();

  }

  render() {
    return (
      <div>
        <Typography variant="h4" gutterBottom>
          Quizzes
        </Typography>
        <Paper>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Nombre del Quiz</TableCell>
                <TableCell />
                <TableCell />
                <TableCell />
                 <TableCell />
              </TableRow>
            </TableHead>
            <TableBody>
              {this.state.quizzes.map(quiz => {
                return (
                  <TableRow key={quiz.id}>
                    <TableCell component="th" scope="quiz">
                      {quiz.name}
                    </TableCell>
                    <TableCell>
                      <Button
                        color="default"
                        component={Link}
                        to={'/quizzes/' + quiz.id}
                      >
                        Ver
                      </Button>
                    </TableCell>
                    <TableCell>
                      <Button
                        color="primary"
                        component={Link}
                        to={'/quizzes/' + quiz.id + '/edit'}
                      >
                        Editar
                      </Button>
                    </TableCell>
                    <TableCell>
                      <form onSubmit={this.delete}>
                        <input type="hidden" name="id" value={quiz.id} />
                        <Button color="secondary" type="submit">
                          Borrar
                        </Button>
                      </form>
                    </TableCell>
                     <TableCell>
                      <form onSubmit={this.generateQuiz}>
                        <input type="hidden" name="id" value={quiz.id} />
                        <Button color="default" type="submit">
                          Generar Quiz
                        </Button>
                      </form>
                    </TableCell>

                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </Paper>

        <div className="btn-group">
          <Button
            variant="contained"
            color="primary"
            className="btn-primary"
            component={Link}
            to="/quizzes/create"
          >
            Crear Nuevo Quiz
          </Button>
        </div>
      </div>
    );
  }
}

export default withSnackbar(QuizIndexComponent);
