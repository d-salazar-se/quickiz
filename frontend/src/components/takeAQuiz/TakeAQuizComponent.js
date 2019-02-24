import React, { Component } from 'react';

import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Highlight } from 'react-fast-highlight';


import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';

class TakeAQuizComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      quiz: {
        id: this.props.match.params.id,
        studentName: '',
        startAt: '',
        finishAt: '',
        quizStructure: {},
        questionInstances: [],
      },
      questions: [],
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    //this.getQuestion = this.getQuestion.bind(this);
  }

  componentDidMount() {
    this.getQuiz();
  }

  getQuiz() {
    axios
      .get(process.env.REACT_APP_API_URL + '/quizzes/' + this.state.quiz.id)
      .then(result => {
        this.setState({
          quiz: result.data,
        });

        // let promises = [];

        // this.state.quiz.questionInstances.forEach(q => {
        //   let ajax = axios.get(
        //     process.env.REACT_APP_API_URL + '/questions/' + q.questionId
        //   );
        //   promises.push(ajax);
        // });

        // axios.all(promises).then(res => {
        //   console.log('res', res);
        //   res.forEach(r => {
        //     this.setState(previousState => ({
        //       questions: [...previousState.questions, r.data],
        //     }));
        //   });
        //   console.log('QUESTIONS: ', this.state.questions);
        // });
          this.setState({
            isLoading: false,
          });
      })
      .catch(error => {
        this.setState({
          quiz: [],
          isLoading: false,
        });
        this.props.enqueueSnackbar('Error al cargar los datos!', {
          variant: 'error',
        });
      });
  }

  validateSubmit(event) {
    const quiz = this.state.quiz;
    var outcome = true;
    try {
      quiz.questionInstances &&
        quiz.questionInstances.forEach(item => {
          if (
            !item.studentAnswer ||
            item.studentAnswer.length === 0 ||
            item.studentAnswer.length > 256
          ) {
            outcome = false;
          }
        });
    } catch (err) {
      this.props.enqueueSnackbar('Error al validar los datos!', {
        variant: 'error',
      });
    }

    event.preventDefault();
    event.stopPropagation();

    if (!outcome) {
      this.props.enqueueSnackbar(
        'Debes completar todos los campos correctamente!',
        { variant: 'warning' }
      );
    }
    return outcome;
  }

  handleSubmit(event) {
    this.setState({ isLoading: true });
    if (!this.validateSubmit(event)) {
      this.setState({ isLoading: false });
      event.preventDefault();
      return;
    }

    axios.put(process.env.REACT_APP_API_URL + '/quizzes/' + this.state.quiz.id, this.state.quiz)
      .then(result => {
        this.setState({ isLoading: false });
        this.props.history.push('/my/quizzes');
        this.props.enqueueSnackbar('Quiz enviado!', { variant: 'success' });
      })
      .catch(error => {
        this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al actualizar los datos!', {
          variant: 'error',
        });
      });
    event.preventDefault();
  }

  onUpdateItem = (i, answer) => {
    this.setState(state => {
      const list = state.quiz.questionInstances.map((item, j) => {
        if (j === i) {
          item.studentAnswer = answer;
          return answer;
        } else {
          return item;
        }
      });

      return {
        list,
      };
    });
  };

  render() {
    return (
      <div>
        {this.state.isLoading ? (
          <LinearProgress className="progress" />
        ) : (
          <div>
            <form onSubmit={this.handleSubmit}>
              <div className="panelcrear">
                <div className="formcrear">
                  <Grid container direction="row" justify="center" alignItems="center" spacing={24}>
                    <Grid item xs={12}>
                      <Typography variant="h4" gutterBottom>
                        { this.state.quiz.quizStructure.name }
                      </Typography>
                    </Grid>

                    <Grid item xs={12}>
                      <Typography variant="h5" gutterBottom>
                        Duración: { this.state.quiz.quizStructure.duration } min.
                      </Typography>
                    </Grid>
                  </Grid>

                  <Grid container direction="row" alignItems="center" spacing={24}>
                    <Grid item xs={12}>
                      <Table>
                        <TableBody>
                          {this.state.quiz.questionInstances.map(
                            (questionInstance, index) => {
                              // question = this.state.questions[index];
                              // questionInstance.variableValues = JSON.parse(questionInstance.variableValues);
                              console.log(questionInstance.variableValues);
                              return (
                                <TableRow key={index}>
                                  <TableCell scope="questionInstance">
                                    <h3>[{questionInstance.question.score} pts] {questionInstance.question.title}</h3>

                                    <Highlight languages={['python']}>
                                      { questionInstance.variableValues }
                                    </Highlight>

                                    <Highlight languages={['python']}>
                                      {questionInstance.question.code}
                                    </Highlight>
                                    <TextField
                                      label="Respuesta"
                                      fullWidth
                                      placeholder=" "
                                      helperText="Ingrese su respuesta"
                                      margin="normal"
                                      variant="outlined"
                                      onChange={e =>
                                        this.onUpdateItem(index, e.target.value)
                                      }
                                      defaultValue=""
                                      InputLabelProps={{
                                        shrink: true,
                                      }}
                                    />
                                    <p>
                                      <br />
                                    </p>
                                  </TableCell>
                                </TableRow>
                              );
                            }
                          )}
                        </TableBody>
                      </Table>
                    </Grid>
                    <Grid item xs={12}>
                      <Button variant="contained" color="primary" type="submit">
                        Enviar mis respuestas
                      </Button>
                    </Grid>
                  </Grid>
                </div>
              </div>
            </form>
          </div>
        )}
      </div>
    );
  }
}

export default withSnackbar(TakeAQuizComponent);
