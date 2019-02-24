import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withStyles } from '@material-ui/core/styles';
import PropTypes from 'prop-types';

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import Fab from '@material-ui/core/Fab';
import AddIcon from '@material-ui/icons/Add';
import Icon from '@material-ui/core/Icon';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';

//

class QuizCreateQuestionsComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      questionStructures: {
        score: 1,
        quizStructureId: this.props.match.params.id,
        poolId: 0,
      },
      pools: [],

      count: 1,
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleClick = this.handleClick.bind(this);
    this.handleClickDelete = this.handleClickDelete.bind(this);
  }

  componentDidMount() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools')
      .then(res => {
        this.setState({
          pools: res.data,
        });
      })
      .catch(err => console.log(err));
  }

  validateSubmit(event) {
    // const form = event.target;
    const quiz = this.state.quiz;
    var outcome = true;
    // const properties = [
    //   'name'
    // ];

    try {
      if (quiz.name.length === 0 || quiz.name.length > 256) {
        outcome = false;
        console.error('quiz.name invalid');
        // form.elements['name'].classList += " invalid";
        // window.M.toast({html: 'Largo del nombre no puede ser mayor a 256 caracteres'});
      }
    } catch (err) {
      console.log(err);
      // window.M.toast({html: 'Whoops, ha ocurrido un error'});
    }

    event.preventDefault();
    event.stopPropagation();
    return outcome;
  }

  handleClick() {
    this.setState({ count: this.state.count + 1 }); //contador de preguntas
    console.log(this.state.count);
    //console.log(this.state.quiz.id);
  }
  handleClickDelete() {
    this.setState({ count: this.state.count - 1 }); //contador de preguntas
    console.log(this.state.count);
  }

  createQuestions = () => {
    let table = [];
    for (let i = 0; i < this.state.count; i++) {
      table.push(
        <Grid container direction="row" justify="center" alignItems="center">
          {' '}
          <TextField
            id="outlined-number"
            name="score"
            type="number"
            value={this.state.questionStructures.score}
            helperText="Puntaje por pregunta"
            InputLabelProps={{
              shrink: true,
            }}
            inputProps={{
              min: '0.1',
              max: '6',
              step: '0.1',
            }}
            margin="normal"
            variant="outlined"
          />
          <TextField
            id="outlined-select-currency-native"
            select
            name="poolId"
            value={this.state.pools.name}
            SelectProps={{
              native: true,
            }}
            helperText="Seleccione un pozo"
            margin="normal"
            variant="outlined"
          >
            {this.state.pools.map(option => (
              <option key={option.id} value={option.id}>
                {option.name}
              </option>
            ))}
          </TextField>
          <IconButton onClick={this.handleClickDelete} aria-label="Delete">
            <DeleteIcon fontSize="small" />
          </IconButton>
        </Grid>
      );
    }
    return table;
  };

  handleSubmit(event) {
    if (!this.validateSubmit(event)) {
      event.preventDefault();
      return;
    }

    axios
      .post(
        process.env.REACT_APP_API_URL + '/questionStructures',
        this.state.questionStructures
      )
      .then(result => {
        this.setState({
          questionStructures: result.data,
        });

        console.log('done');
        console.log(result.data);
      })
      .catch(error => {
        console.log(error);
      });

    event.preventDefault();
  }

  handleChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      quiz: {
        ...this.state.quiz,
        [name]: value,
      },
    });
  }

  render() {
    const { classes } = this.props;
    return (
      <div class="panelcrear">
        <div class="formcrear">
          <Typography variant="h4" gutterBottom>
            Crear Quiz
          </Typography>

          <form onSubmit={this.handleSubmit}>
            <Grid
              container
              direction="row"
              justify="flex-end"
              alignItems="center"
            >
              <Button
                variant="contained"
                color="primary"
                onClick={this.handleClick}
              >
                Añadir Pregunta <AddIcon />
              </Button>
            </Grid>

            <Grid
              container
              direction="row"
              justify="center"
              alignItems="center"
            >
              <div className="panel_depreguntas">{this.createQuestions()}</div>
            </Grid>
            <div className="btn-group">
              <Button component={Link} to="/quizzes">
                Cancelar
              </Button>

              <Button
                variant="contained"
                color="primary"
                className="btn-primary"
                type="submit"
              >
                Crear
              </Button>
            </div>
          </form>
        </div>
      </div>
    );
  }
}

export default QuizCreateQuestionsComponent;
